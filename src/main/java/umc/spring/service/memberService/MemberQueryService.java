package umc.spring.service.memberService;

import umc.spring.domain.Member;

import java.util.List;

public interface MemberQueryService {

    List<Member> findMemberById(Long memberId);
}
