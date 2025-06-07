package umc.spring.service.memberService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.FoodHandler;
import umc.spring.apiPayload.exception.handler.TempHandler;
import umc.spring.config.security.CustomUserDetails;
import umc.spring.config.security.CustomUserDetailsService;
import umc.spring.config.security.jwt.JwtTokenProvider;
import umc.spring.converter.FoodCategoryConverter;
import umc.spring.converter.MemberConverter;
import umc.spring.domain.Food;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.FoodCategory;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.foodRepository.FoodRepository;
import umc.spring.repository.memberMissionRepository.MemberMissionRepository;
import umc.spring.repository.memberRepository.MemberRepository;
import umc.spring.repository.missionRepository.MissionRepository;
import umc.spring.repository.redisRepository.LogoutAccessTokenRepository;
import umc.spring.repository.redisRepository.RefreshTokenRepository;
import umc.spring.web.dto.MemberRequestDTO;
import umc.spring.web.dto.MemberResponseDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;

    private final FoodRepository foodRepository;

    private final MemberMissionRepository memberMissionRepository;

    private final MissionRepository missionRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    private final LogoutAccessTokenRepository logoutAccessTokenRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    @Transactional
    public Member joinMember(MemberRequestDTO.JoinMemberDTO request) {

        Member newMember = MemberConverter.toMember(request);

        newMember.encodePassword(passwordEncoder.encode(request.getPassword()));

        List<Food> foodList = request.getFoodList().stream()
                .map(food -> foodRepository.findById(food)
                        .orElseThrow(() -> new FoodHandler(ErrorStatus.FOOD_NOT_FOUND))).collect(Collectors.toList());

        List<FoodCategory> foodCategoryList = FoodCategoryConverter.toFoodCategoryList(foodList);

        foodCategoryList.forEach(foodCategory -> {foodCategory.setMember(newMember);});

        return memberRepository.save(newMember);
    }

    @Override
    @Transactional
    public MemberMission challengeMission(Long memberId, Long missionId) {

        Mission mission = missionRepository.findById(missionId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);

        MemberMission newMemberMission = MemberMission.builder()
                .status(MissionStatus.ONGOING)
                .build();
        newMemberMission.setMember(member);
        newMemberMission.setMission(mission);

        return memberMissionRepository.save(newMemberMission);
    }

    @Override
    @Transactional
    public void completeMission(Long memberId, Long missionId) {

        Mission mission = missionRepository.findById(missionId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);

        MemberMission memberMission = memberMissionRepository.findByMemberAndMission(member,mission);
        if (memberMission == null) {
            throw new TempHandler(ErrorStatus.MISSION_NOT_ONGOING);
        }
        memberMission.missionComplete(MissionStatus.COMPLETED);
    }

    @Override
    public MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginRequestDTO request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new TempHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new TempHandler(ErrorStatus.INVALID_PASSWORD);
        }

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(String.valueOf(member.getId()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null,
                Collections.singleton(() -> member.getRole().name())
        );

        String accessToken = jwtTokenProvider.generateAccessToken(authentication);
        String refreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        refreshTokenRepository.saveRefreshToken(member.getId(), refreshToken);

        return MemberConverter.toLoginResultDTO(member.getId(), accessToken, refreshToken);
    }

    @Override
    public MemberResponseDTO.LoginResultDTO reissue(String refreshToken) {
        jwtTokenProvider.validateToken(refreshToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(refreshToken);
        Long memberId = ((CustomUserDetails) authentication.getPrincipal()).getId();

        if (!refreshTokenRepository.findRefreshToken(memberId))
            throw new TempHandler(ErrorStatus.REFRESH_TOKEN_NOT_FOUND);
        refreshTokenRepository.getRefreshToken(memberId);
        refreshTokenRepository.deleteRefreshToken(memberId);

        String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication);

        refreshTokenRepository.saveRefreshToken(memberId, newRefreshToken);

        return MemberConverter.toLoginResultDTO(memberId, newAccessToken, newRefreshToken);
    }

    @Override
    public void logout(HttpServletRequest request) {
        String accessToken = JwtTokenProvider.resolveToken(request);
        jwtTokenProvider.validateToken(accessToken);
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        Long memberId = ((CustomUserDetails) authentication.getPrincipal()).getId();

        logoutAccessTokenRepository.saveLogoutAccessToken(memberId, accessToken);

        refreshTokenRepository.getRefreshToken(memberId);
        refreshTokenRepository.deleteRefreshToken(memberId);
    }
}
