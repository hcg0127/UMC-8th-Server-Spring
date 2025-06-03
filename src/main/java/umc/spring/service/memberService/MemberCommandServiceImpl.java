package umc.spring.service.memberService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.FoodHandler;
import umc.spring.converter.FoodCategoryConverter;
import umc.spring.converter.MemberConverter;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Food;
import umc.spring.domain.Member;
import umc.spring.domain.Mission;
import umc.spring.domain.mapping.FoodCategory;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.foodRepository.FoodRepository;
import umc.spring.repository.memberMissionRepository.MemberMissionRepository;
import umc.spring.repository.memberRepository.MemberRepository;
import umc.spring.repository.missionRepository.MissionRepository;
import umc.spring.web.dto.MemberRequestDTO;
import umc.spring.web.dto.MissionRequestDTO;

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

    @Override
    @Transactional
    public Member joinMember(MemberRequestDTO.JoinMemberDTO request) {

        Member newMember = MemberConverter.toMember(request);

        List<Food> foodList = request.getFoodList().stream()
                .map(food -> {
                    return foodRepository.findById(food).orElseThrow(() -> new FoodHandler(ErrorStatus.FOOD_NOT_FOUND));
                }).collect(Collectors.toList());

        List<FoodCategory> foodCategoryList = FoodCategoryConverter.toFoodCategoryList(foodList);

        foodCategoryList.forEach(foodCategory -> {foodCategory.setMember(newMember);});

        return memberRepository.save(newMember);
    }

    @Override
    @Transactional
    public MemberMission challengeMission(Long memberId, Long missionId, MissionRequestDTO.MissionChallengeDTO request) {

        Mission mission = missionRepository.findById(missionId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);

        MemberMission newMemberMission = MissionConverter.toMemberMission(request);

        newMemberMission.setMember(member);
        newMemberMission.setMission(mission);

        return memberMissionRepository.save(newMemberMission);
    }
}
