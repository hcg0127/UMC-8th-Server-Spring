package umc.spring.service.memberMissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.repository.memberMissionRepository.MemberMissionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberMissionQueryServiceImpl implements MemberMissionQueryService {

    private final MemberMissionRepository memberMissionRepository;

    @Override
    public List<MemberMission> findMemberMissionsByMemberId(Long memberId, int offset, int limit) {

        List<MemberMission> filteredMemberMissions = memberMissionRepository.findMemberMissionsByMemberId(offset, limit, memberId);

        filteredMemberMissions
                .forEach(memberMission -> System.out.println("MemberMission: " + memberMission));

        return filteredMemberMissions;
    }
}
