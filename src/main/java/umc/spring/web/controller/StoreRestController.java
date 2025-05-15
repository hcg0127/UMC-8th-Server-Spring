package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import umc.spring.validation.annotation.ExistMember;
import umc.spring.validation.annotation.ExistStore;
import umc.spring.web.dto.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/stores")
public class StoreRestController {

    private final StoreCommandService storeCommandService;

    private final MissionCommandService missionCommandService;

    @PostMapping()
    @Operation(summary = "특정 지역에 가게 추가 API", description = "특정 지역에 가게를 추가하는 API 입니다.")
    public ApiResponse<StoreResponseDTO.JoinResultDTO> joinStore(@Valid @RequestBody StoreRequestDTO.JoinDTO request) {
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
}
