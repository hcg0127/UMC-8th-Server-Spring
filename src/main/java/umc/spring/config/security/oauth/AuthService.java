package umc.spring.config.security.oauth;

import jakarta.servlet.http.HttpServletResponse;
import umc.spring.domain.Member;

public interface AuthService {
    Member oAuthLogin(String code, HttpServletResponse res);
}
