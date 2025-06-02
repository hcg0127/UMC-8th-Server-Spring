package umc.spring.converter;

import org.springframework.data.domain.Page;
import umc.spring.domain.Review;
import umc.spring.web.dto.ReviewRequestDTO;
import umc.spring.web.dto.ReviewResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewConverter {

    // 리뷰로 변환
    public static Review toReview(ReviewRequestDTO.ReviewCreateDTO request) {
        return Review.builder()
                .content(request.getBody())
                .star(request.getStar())
                .build();
    }

    // 리뷰 작성 DTO로 변환
    public static ReviewResponseDTO.ReviewCreateResultDTO toCreateReviewResultDTO(Review review) {
        return ReviewResponseDTO.ReviewCreateResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    // 가게 리뷰 정보
    public static ReviewResponseDTO.StoreReviewPreViewDTO storeReviewPreViewDTO(Review review) {
        return ReviewResponseDTO.StoreReviewPreViewDTO.builder()
                .ownerNickname(review.getMember().getName())
                .score(review.getStar())
                .body(review.getContent())
                .createdAt(review.getCreatedAt().toLocalDate())
                .build();
    }

    // 가게 리뷰 목록
    public static ReviewResponseDTO.StoreReviewPreViewListDTO storeReviewPreViewListDTO(Page<Review> reviewList) {
        List<ReviewResponseDTO.StoreReviewPreViewDTO> storeReviewPreViewDTOList = reviewList.stream()
                .map(ReviewConverter::storeReviewPreViewDTO).toList();

        return ReviewResponseDTO.StoreReviewPreViewListDTO.builder()
                .isFirst(reviewList.isFirst())
                .isLast(reviewList.isLast())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .listSize(storeReviewPreViewDTOList.size())
                .reviewList(storeReviewPreViewDTOList)
                .build();
    }

    // 내 리뷰 정보
    public static ReviewResponseDTO.MyReviewPreViewDTO myReviewPreViewDTO(Review review) {
        return ReviewResponseDTO.MyReviewPreViewDTO.builder()
                .ownerNickname(review.getMember().getName())
                .storeNickname(review.getStore().getName())
                .score(review.getStar())
                .body(review.getContent())
                .createdAt(review.getCreatedAt().toLocalDate())
                .build();
    }

    // 내 리뷰 목록
    public static ReviewResponseDTO.MyReviewPreViewListDTO myReviewPreViewListDTO(Page<Review> reviewList) {
        List<ReviewResponseDTO.MyReviewPreViewDTO> myReviewPreViewDTOList = reviewList.stream()
                .map(ReviewConverter::myReviewPreViewDTO).toList();

        return ReviewResponseDTO.MyReviewPreViewListDTO.builder()
                .isFirst(reviewList.isFirst())
                .isLast(reviewList.isLast())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .listSize(myReviewPreViewDTOList.size())
                .reviewList(myReviewPreViewDTOList)
                .build();
    }
}
