package umc.spring.service.memberService;

import umc.spring.domain.Member;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MemberRequestDTO;

public interface MemberCommandService {
    Member joinMember(MemberRequestDTO.JoinMemberDTO request);

    MemberMission challengeMission(Long memberId, Long missionId);

    void completeMission(Long memberId, Long missionId);
}
