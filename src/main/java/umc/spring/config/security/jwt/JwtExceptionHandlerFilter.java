package umc.spring.config.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import umc.spring.apiPayload.ApiResponse;
import umc.spring.apiPayload.code.BaseErrorCode;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.TempHandler;

import java.io.IOException;

@Component
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TempHandler e) {
            setErrorResponse(response, e.getCode(), e);
        } catch (Exception e) {
            setErrorResponse(response, ErrorStatus._INTERNAL_SERVER_ERROR, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, BaseErrorCode baseErrorCode, Throwable e) throws IOException {
        response.setStatus(baseErrorCode.getReasonHttpStatus().getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<Object> res = ApiResponse
                .onFailure(baseErrorCode.getReasonHttpStatus().getCode(),
                        baseErrorCode.getReasonHttpStatus().getMessage(),
                        e.getMessage());
        response.getWriter().write(mapper.writeValueAsString(res));
    }
}
