package umc.spring.service.missionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Mission;
import umc.spring.repository.missionRepository.MissionRepository;
import umc.spring.repository.storeRepository.StoreRepository;
import umc.spring.web.dto.MissionRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionCommandServiceImpl implements MissionCommandService {

    private final MissionRepository missionRepository;

    private final StoreRepository storeRepository;

    @Override
    @Transactional
    public Mission createMission(Long storeId, MissionRequestDTO.MissionCreateDTO request) {

        Mission newMission = MissionConverter.toMission(request);
        newMission.setStore(storeRepository.findById(storeId).get());

        return missionRepository.save(newMission);
    }
}
