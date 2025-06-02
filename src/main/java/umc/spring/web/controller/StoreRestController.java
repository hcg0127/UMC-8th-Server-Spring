package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.converter.StoreConverter;
import umc.spring.domain.Mission;
import umc.spring.domain.Review;
import umc.spring.domain.Store;
import umc.spring.service.missionService.MissionCommandService;
import umc.spring.service.storeService.StoreCommandService;
import umc.spring.service.storeService.StoreQueryService;
import umc.spring.validation.annotation.CheckPage;
import umc.spring.validation.annotation.ExistMember;
import umc.spring.validation.annotation.ExistStore;
import umc.spring.web.dto.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreRestController {

    private final StoreCommandService storeCommandService;

    private final StoreQueryService storeQueryService;

    private final MissionCommandService missionCommandService;

    @PostMapping()
    @Operation(summary = "특정 지역에 가게 추가 API", description = "특정 지역에 가게를 추가하는 API 입니다.")
    public ApiResponse<StoreResponseDTO.JoinResultDTO> joinStore(@Valid @RequestBody StoreRequestDTO.JoinStoreDTO request) {
        Store newStore = storeCommandService.joinStore(request);
        return ApiResponse.onSuccess(StoreConverter.toJoinResultDTO(newStore));
    }

    @PostMapping("/{storeId}/missions")
    @Operation(summary = "가게에 미션 추가 API", description = "가게에 미션을 추가하는 API 입니다.")
    public ApiResponse<MissionResponseDTO.MissionCreateResultDTO> createMission(
            @ExistStore @PathVariable("storeId") Long storeId,
            @Valid @RequestBody MissionRequestDTO.MissionCreateDTO request) {
        Mission newMission = missionCommandService.createMission(storeId, request);
        return ApiResponse.onSuccess(MissionConverter.toCreateMissionResultDTO(newMission));
    }

    @PostMapping("/{storeId}/reviews")
    @Operation(summary = "가게에 리뷰 추가 API", description = "가게에 리뷰를 추가하는 API 입니다.")
    public ApiResponse<ReviewResponseDTO.ReviewCreateResultDTO> createReview(
            @ExistStore @PathVariable("storeId") Long storeId,
            @ExistMember @RequestParam("memberId") Long memberId,
            @Valid @RequestBody ReviewRequestDTO.ReviewCreateDTO request) {
        Review newReview = storeCommandService.createReview(storeId, memberId, request);
        return ApiResponse.onSuccess(ReviewConverter.toCreateReviewResultDTO(newReview));
    }

    @GetMapping("/{storeId}/reviews")
    @Operation(summary = "특정 가게의 리뷰 목록 조회 API",
            description = "특정 가게의 리뷰 목록을 조회하는 API이며, 페이징을 포함합니다. query string으로 page 번호를 주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "STORE4001", description = "해당 가게가 없습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "storeId", description = "가게의 아이디, path variable 입니다!"),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다.")
    })
    public ApiResponse<ReviewResponseDTO.StoreReviewPreViewListDTO> getStoreReviewList(
            @ExistStore @PathVariable("storeId") Long storeId,
            @CheckPage @RequestParam("page") Integer page) {
        Page<Review> reviewList = storeQueryService.getStoreReviewList(storeId, page);
        return ApiResponse.onSuccess(ReviewConverter.storeReviewPreViewListDTO(reviewList));
    }
}
