package umc.spring.repository.MissionRepository;

import umc.spring.domain.Mission;

import java.util.List;

public interface MissionRepositoryCustom {

    List<Mission> findMissionsUnchallengedByMemberId(String address, Long memberId, int offset, int limit);
}
