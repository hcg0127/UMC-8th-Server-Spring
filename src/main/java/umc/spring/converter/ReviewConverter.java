package umc.spring.converter;

import umc.spring.domain.Review;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

import java.time.LocalDateTime;

public class ReviewConverter {

    public static Review toReview(ReviewRequestDTO.ReviewCreateDTO request) {
        return Review.builder()
                .content(request.getBody())
                .star(request.getStar())
                .build();
    }

    public static ReviewResponseDTO.ReviewCreateResultDTO toCreateReviewResultDTO(Review review) {
        return ReviewResponseDTO.ReviewCreateResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
