package umc.spring.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ReviewRequestDTO {

    @Getter
    public static class ReviewCreateDTO {
        @NotBlank
        String body;
        @NotNull @Max(5) @Min(0)
        Float star;
    }
}
