package umc.spring.service.MemberService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Member;
import umc.spring.repository.MemberRepository.MemberRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    @Override
    public List<Member> findMemberById(Long memberId) {
        List<Member> filteredMember = memberRepository.findMemberByMemberId(memberId);

        filteredMember
                .forEach(member -> System.out.println("Member: " + member));

        return filteredMember;
    }
}
