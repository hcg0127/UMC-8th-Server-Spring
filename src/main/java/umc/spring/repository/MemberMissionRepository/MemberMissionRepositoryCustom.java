package umc.spring.repository.MemberMissionRepository;

import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;

import java.util.List;

public interface MemberMissionRepositoryCustom {

    List<MemberMission> findMemberMissionsByMemberId(int offset, int limit, Long memberId);
}
