package umc.spring.converter;

import org.springframework.data.domain.Page;
import umc.spring.domain.Mission;
import umc.spring.domain.enums.MissionStatus;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MissionRequestDTO;
import umc.spring.web.dto.MissionResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

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

    // 내 진행 미션 정보
    public static MissionResponseDTO.MyOngoingMissionDTO myOngoingMissionDTO(MemberMission memberMission) {
        return MissionResponseDTO.MyOngoingMissionDTO.builder()
                .storeNickname(memberMission.getMission().getStore().getName())
                .missionStatus(MissionStatus.ONGOING.toString())
                .point(memberMission.getMission().getPoint())
                .price(memberMission.getMission().getPrice())
                .build();
    }


    // 내 진행 미션 목록
    public static MissionResponseDTO.MyOngoingMissionListDTO myOngoingMissionListDTO(Page<MemberMission> missionList) {
        List<MissionResponseDTO.MyOngoingMissionDTO> myOngoingMissionDTOList = missionList.stream()
                .map(MissionConverter::myOngoingMissionDTO).toList();

        return MissionResponseDTO.MyOngoingMissionListDTO.builder()
                .isFirst(missionList.isFirst())
                .isLast(missionList.isLast())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(myOngoingMissionDTOList.size())
                .missionList(myOngoingMissionDTOList)
                .build();
    }
}
