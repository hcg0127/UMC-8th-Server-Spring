package umc.spring.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDateTime;

public class MissionRequestDTO {

    @Getter
    public static class MissionCreateDTO {
        @NotNull
        LocalDateTime deadline;
        @NotNull @Positive
        Integer price;
        @NotNull @Positive
        Integer point;
    }
}
