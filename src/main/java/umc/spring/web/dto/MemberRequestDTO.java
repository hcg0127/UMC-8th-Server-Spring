package umc.spring.web.dto;

import lombok.Getter;

import java.util.List;

public class MemberRequestDTO {

    @Getter
    public static class JoinDTO {
        String name;
        Integer gender;
        Integer birthYear;
        Integer birthMonth;
        Integer birthDay;
        String phoneNumber;
        String address;
        String specAddress;
        List<Long> foodList;
    }
}
