package umc.spring.service.missionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Mission;
import umc.spring.repository.missionRepository.MissionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService {

    private final MissionRepository missionRepository;

    @Override
    public List<Mission> findMissionsUnchallengedByMemberId(String address, Long memberId, int offset, int limit) {

        List<Mission> filteredMissions = missionRepository.findMissionsUnchallengedByMemberId(address, memberId, offset, limit);

        filteredMissions
                .forEach(mission -> System.out.println("Mission: " + mission));

        return filteredMissions;
    }
}
