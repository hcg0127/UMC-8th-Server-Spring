package umc.spring.service.MemberMissionService;

import umc.spring.domain.mapping.MemberMission;

import java.util.List;

public interface MemberMissionQueryService {

    List<MemberMission> findMemberMissionsByMemberId(Long memberId, int offset, int limit);
}
