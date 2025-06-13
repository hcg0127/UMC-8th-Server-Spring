package umc.spring.config.security.oauth;

import umc.spring.domain.Member;
import umc.spring.domain.enums.Gender;
import umc.spring.domain.enums.Role;
import umc.spring.domain.enums.Social;

public class AuthConverter {

    public static Member toMember(String email, String name, String password) {
        return Member.builder()
                .social(Social.KAKAO)
                .role(Role.USER)
                .address("")
                .password(password)
                .gender(Gender.MALE)
                .name(name)
                .email(email)
                .phoneNumber("")
                .build();
    }
}
