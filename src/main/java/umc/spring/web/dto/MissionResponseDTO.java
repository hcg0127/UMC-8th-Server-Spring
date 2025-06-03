package umc.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class MissionResponseDTO {

    // 미션 생성 응답
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MissionCreateResultDTO {
        Long missionId;
        LocalDateTime createdAt;
    }

    // 미션 도전 응답
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MissionChallengeResultDTO {
        Long missionId;
        LocalDateTime createdAt;
    }

    // 내가 진행 중인 미션 목록 조회
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyOngoingMissionListDTO {
        List<MyOngoingMissionDTO> missionList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    // 내가 진행 중인 목록에 담길 미션 정보
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyOngoingMissionDTO {
        String storeNickname;
        String missionStatus;
        Integer point;
        Integer price;
    }
}
