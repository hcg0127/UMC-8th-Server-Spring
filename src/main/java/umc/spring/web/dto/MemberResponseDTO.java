package umc.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class MemberResponseDTO {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class JoinResultDTO {
        Long memberId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResultDTO {
        Long memberId;
        String accessToken;
        String refreshToken;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberInfoDTO {
        String name;
        String email;
        String gender;
    }
}
