package umc.spring.service.storeService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.spring.converter.ReviewConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.repository.memberRepository.MemberRepository;
import umc.spring.repository.reviewRepository.ReviewRepository;
import umc.spring.repository.storeRepository.StoreRepository;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.StoreRequestDTO;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreCommandServiceImpl implements StoreCommandService {

    private final StoreRepository storeRepository;

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Store joinStore(StoreRequestDTO.JoinDTO request) {
        Store newStore = StoreConverter.toStore(request);
        return storeRepository.save(newStore);
    }

    @Override
    @Transactional
    public Review createReview(Long memberId, Long storeId,
                               ReviewRequestDTO.ReviewCreateDTO request) {
        Review newReview = ReviewConverter.toReview(request);

        newReview.setMember(memberRepository.findById(memberId).get());
        newReview.setStore(storeRepository.findById(storeId).get());

        return reviewRepository.save(newReview);
    }
}
