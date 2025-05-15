package umc.spring.repository.missionRepository;

import umc.spring.domain.Mission;

import java.util.List;

public interface MissionRepositoryCustom {

    List<Mission> findMissionsUnchallengedByMemberId(String address, Long memberId, int offset, int limit);
}
