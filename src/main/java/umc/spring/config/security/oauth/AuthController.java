package umc.spring.config.security.oauth;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.converter.MemberConverter;
import umc.spring.domain.Member;
import umc.spring.web.dto.MemberResponseDTO;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/login/oauth2/code/kakao")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> kakaoLogin(@RequestParam("code") String code,
                                                     HttpServletResponse res) {
        Member member = authService.oAuthLogin(code, res);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }
}
