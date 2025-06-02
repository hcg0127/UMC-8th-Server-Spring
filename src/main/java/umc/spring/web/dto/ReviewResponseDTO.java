package umc.spring.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDTO {

    // 리뷰 작성
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReviewCreateResultDTO {
        Long reviewId;
        LocalDateTime createdAt;
    }

    // 가게 리뷰 목록 조회
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StoreReviewPreViewListDTO {
        List<StoreReviewPreViewDTO> reviewList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    // 가게 리뷰 목록에 담길 리뷰 정보
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class StoreReviewPreViewDTO {
        String ownerNickname;
        Float score;
        String body;
        LocalDate createdAt;
    }

    // 특정 사용자 리뷰 목록 조회
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyReviewPreViewListDTO {
        List<MyReviewPreViewDTO> reviewList;
        Integer listSize;
        Integer totalPage;
        Long totalElements;
        Boolean isFirst;
        Boolean isLast;
    }

    // 특정 사용자 리뷰 목록에 담길 리뷰 정보
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyReviewPreViewDTO {
        String ownerNickname;
        String storeNickname;
        Float score;
        String body;
        LocalDate createdAt;
    }
}
