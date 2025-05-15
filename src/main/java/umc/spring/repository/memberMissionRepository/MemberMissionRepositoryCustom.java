package umc.spring.repository.memberMissionRepository;

import umc.spring.domain.mapping.MemberMission;

import java.util.List;

public interface MemberMissionRepositoryCustom {

    List<MemberMission> findMemberMissionsByMemberId(int offset, int limit, Long memberId);

    List<MemberMission> findOngoingMemberMissionByMissionId(Long missionId);
}
