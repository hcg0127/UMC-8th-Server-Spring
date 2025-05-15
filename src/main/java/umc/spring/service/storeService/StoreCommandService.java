package umc.spring.service.storeService;

import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.StoreRequestDTO;

public interface StoreCommandService {

    Store joinStore(StoreRequestDTO.JoinDTO request);

    Review createReview(Long memberId, Long storeId, ReviewRequestDTO.ReviewCreateDTO request);
}
