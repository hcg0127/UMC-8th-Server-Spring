package umc.spring.service.missionService;

import umc.spring.domain.Mission;

import java.util.List;

public interface MissionQueryService {

    List<Mission> findMissionsUnchallengedByMemberId(String address, Long memberId, int offset, int limit);
}
