package umc.spring.converter;

import umc.spring.domain.Mission;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MissionRequestDTO;
import umc.spring.web.dto.MissionResponseDTO;

import java.time.LocalDateTime;

public class MissionConverter {

    public static Mission toMission(MissionRequestDTO.MissionCreateDTO request) {
        return Mission.builder()
                .price(request.getPrice())
                .point(request.getPoint())
                .deadline(request.getDeadline())
                .build();
    }

    public static MissionResponseDTO.MissionCreateResultDTO toCreateMissionResultDTO(Mission mission) {
        return MissionResponseDTO.MissionCreateResultDTO.builder()
                .missionId(mission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static MemberMission toMemberMission(MissionRequestDTO.MissionChallengeDTO request) {
        return MemberMission.builder()
                .status((request.getStatus()==0 ? MissionStatus.ONGOING : MissionStatus.COMPLETED))
                .build();
    }

    public static MissionResponseDTO.MissionChallengeResultDTO toChallengeResultDTO(MemberMission memberMission) {
        return MissionResponseDTO.MissionChallengeResultDTO.builder()
                .missionId(memberMission.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
