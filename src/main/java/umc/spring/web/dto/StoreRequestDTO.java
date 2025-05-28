package umc.spring.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class StoreRequestDTO {

    @Getter
    public static class JoinStoreDTO {
        @NotBlank
        String name;
        @NotBlank @Size(min = 10, max = 100)
        String address;
    }
}
