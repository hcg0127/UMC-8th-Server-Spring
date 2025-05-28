package umc.spring.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberConverter;
import umc.spring.converter.MissionConverter;
import umc.spring.domain.Member;
import umc.spring.domain.mapping.MemberMission;
import umc.spring.service.memberService.MemberCommandService;
import umc.spring.validation.annotation.ExistMember;
import umc.spring.validation.annotation.ExistMission;
import umc.spring.validation.annotation.NotOngoingMission;
import umc.spring.web.dto.MemberRequestDTO;
import umc.spring.web.dto.MemberResponseDTO;
import umc.spring.web.dto.MissionRequestDTO;
import umc.spring.web.dto.MissionResponseDTO;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberRestController {

    private final MemberCommandService memberCommandService;

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
}
