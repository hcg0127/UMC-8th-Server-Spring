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
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberConverter;
import umc.spring.converter.MissionConverter;
import umc.spring.converter.ReviewConverter;
import umc.spring.domain.Member;
import umc.spring.domain.Review;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.memberService.MemberCommandService;
import umc.spring.service.memberService.MemberQueryService;
import umc.spring.validation.annotation.CheckPage;
import umc.spring.validation.annotation.ExistMember;
import umc.spring.validation.annotation.ExistMission;
import umc.spring.validation.annotation.NotOngoingMission;
import umc.spring.web.dto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberCommandService memberCommandService;

    private final MemberQueryService memberQueryService;

    @PostMapping
    @Operation(summary = "회원가입 API", description = "회원가입 API 입니다.")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> joinMember(@RequestBody @Valid MemberRequestDTO.JoinMemberDTO request) {
        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/{memberId}/missions/{missionId}/challenge")
    @Operation(summary = "미션 도전 API", description = "미션 도전하기 API 입니다.")
    public ApiResponse<MissionResponseDTO.MissionChallengeResultDTO> missionChallenge(
            @Valid @RequestBody MissionRequestDTO.MissionChallengeDTO request,
            @ExistMember @PathVariable("memberId") Long memberId,
            @NotOngoingMission @ExistMission @PathVariable("missionId") Long missionId) {
        MemberMission newMemberMission = memberCommandService.challengeMission(memberId, missionId, request);
        return ApiResponse.onSuccess(MissionConverter.toChallengeResultDTO(newMemberMission));
    }

    @GetMapping("/{memberId}/reviews")
    @Operation(summary = "내가 작성한 리뷰 조회 API", description = "내가 작성한 리뷰를 조회하는 API이며, 페이징을 포함합니다. queryString으로 page 번호와 사용자의 아이디를 주세요.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "사용자의 아이디, path variable 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다.")
    })
    public ApiResponse<ReviewResponseDTO.MyReviewPreViewListDTO> getMyReviewList(
            @ExistMember @PathVariable("memberId") Long memberId,
            @CheckPage @RequestParam("page") Integer page) {
        Page<Review> reviewList = memberQueryService.getMyReviewList(memberId, page);
        return ApiResponse.onSuccess(ReviewConverter.myReviewPreViewListDTO(reviewList));
    }

    @GetMapping("/{memberId}/missions")
    @Operation(summary = "내가 진행 중인 미션 목록 조회 API", description = "내가 진행 중인 미션 목록을 조회하는 API이며, 페이징을 포함합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200",description = "OK, 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "memberId", description = "사용자의 아이디, path variable 입니다."),
            @Parameter(name = "page", description = "페이지 번호, 1번이 1 페이지 입니다.")
    })
    public ApiResponse<MissionResponseDTO.MyOngoingMissionListDTO> getMyOngoingMissionList(
            @ExistMember @PathVariable("memberId") Long memberId,
            @CheckPage @RequestParam("page") Integer page) {
        Page<MemberMission> missionList = memberQueryService.getMyOngoingMissionList(memberId, page);
        return ApiResponse.onSuccess(MissionConverter.myOngoingMissionListDTO(missionList));
    }
}
