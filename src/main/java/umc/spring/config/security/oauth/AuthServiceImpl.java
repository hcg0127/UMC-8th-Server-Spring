package umc.spring.config.security.oauth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import umc.spring.config.security.CustomUserDetailsService;
import umc.spring.config.security.jwt.JwtTokenProvider;
import umc.spring.config.security.oauth.kakao.KakaoDTO;
import umc.spring.config.security.oauth.kakao.KakaoUtil;
import umc.spring.domain.Member;
import umc.spring.repository.memberRepository.MemberRepository;

import java.util.Collections;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final KakaoUtil kakaoUtil;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    public Member oAuthLogin(String code, HttpServletResponse res) {
        KakaoDTO.OAuthToken oAuthToken = kakaoUtil.requestToken(code);
        KakaoDTO.KakaoProfile kakaoProfile = kakaoUtil.requestProfile(oAuthToken);

        Optional<Member> queryMember = memberRepository
                .findByEmail(kakaoProfile.getKakao_account().getEmail());
        Member member = null;
        if (queryMember.isPresent()) {
            member = queryMember.get();
        } else {
            member = AuthConverter.toMember(kakaoProfile.getKakao_account().getEmail(),
                    kakaoProfile.getKakao_account().getProfile().getNickname(),
                    passwordEncoder.encode("1234"));
            memberRepository.save(member);
        }

        res.setHeader("Authorization",
                jwtTokenProvider.generateAccessToken(getAuthentication(member)));
        return member;
    }

    private Authentication getAuthentication(Member member) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(String.valueOf(member.getId()));
        return new UsernamePasswordAuthenticationToken(
                userDetails, null,
                Collections.singleton(() -> member.getRole().name())
        );
    }
}
