package umc.spring.service.memberService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.memberRepository.MemberRepository;
import umc.spring.repository.reviewRepository.ReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryServiceImpl implements MemberQueryService {

    private final MemberRepository memberRepository;

    private final ReviewRepository reviewRepository;

    @Override
    public List<Member> findMemberById(Long memberId) {
        List<Member> filteredMember = memberRepository.findMemberByMemberId(memberId);

        filteredMember
                .forEach(member -> System.out.println("Member: " + member));

        return filteredMember;
    }

    @Override
    public Page<Review> getMyReviewList(Long memberId, Integer page) {
        System.out.println("memberId = " + memberId);
        Member member = memberRepository.findById(memberId).orElse(null);

        return reviewRepository.findAllByMember(member, PageRequest.of(page-1, 10));
    }
}
