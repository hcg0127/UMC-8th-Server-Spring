package umc.spring.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import umc.spring.domain.enums.Role;
import umc.spring.validation.annotation.ExistCategories;

import java.util.List;

public class MemberRequestDTO {

    @Getter
    @Setter // thymeleaf에서 사용
    public static class JoinMemberDTO {
        @NotBlank
        String name;
        @NotBlank @Email
        String email;
        @NotBlank
        String password;
        @NotNull
        Role role;
        @NotNull
        Integer gender;
        @NotNull
        Integer birthYear;
        @NotNull
        Integer birthMonth;
        @NotNull
        Integer birthDay;
        @NotNull
        String phoneNumber;
        @Size(min = 5, max = 20)
        String address;
        @Size(min = 5, max = 20)
        String specAddress;
        @ExistCategories
        List<Long> foodList;
    }

    @Getter
    @Setter
    public static class LoginRequestDTO {
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;
        @NotBlank(message = "패스워드는 필수입니다.")
        private String password;
    }
}
