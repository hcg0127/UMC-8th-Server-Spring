package umc.spring.repository.memberRepository;

import umc.spring.domain.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMemberByMemberId(Long memberId);
}
