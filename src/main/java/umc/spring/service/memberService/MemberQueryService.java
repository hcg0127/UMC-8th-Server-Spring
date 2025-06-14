package umc.spring.service.memberService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.web.dto.MemberResponseDTO;

import java.util.List;

public interface MemberQueryService {

    List<Member> findMemberById(Long memberId);

    Page<Review> getMyReviewList(Long memberId, Integer page);

    Page<MemberMission> getMyOngoingMissionList(Long memberId, Integer page);

    MemberResponseDTO.MemberInfoDTO getMemberInfo(HttpServletRequest request);
}
