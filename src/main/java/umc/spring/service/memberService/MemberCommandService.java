package umc.spring.service.memberService;

import jakarta.servlet.http.HttpServletRequest;
import umc.spring.domain.Member;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MemberRequestDTO;
import umc.spring.web.dto.MemberResponseDTO;

public interface MemberCommandService {
    Member joinMember(MemberRequestDTO.JoinMemberDTO request);

    MemberMission challengeMission(Long memberId, Long missionId);

    void completeMission(Long memberId, Long missionId);

    MemberResponseDTO.LoginResultDTO loginMember(MemberRequestDTO.LoginRequestDTO request);

    MemberResponseDTO.LoginResultDTO reissue(String refreshToken);

    void logout(HttpServletRequest request);
}
