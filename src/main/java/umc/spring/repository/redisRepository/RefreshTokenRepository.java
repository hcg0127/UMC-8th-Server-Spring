package umc.spring.repository.redisRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.apiPayload.code.status.ErrorStatus;
import umc.spring.apiPayload.exception.handler.TempHandler;
import umc.spring.config.security.jwt.JwtTokenProvider;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final StringRedisTemplate redisTemplate;

    private final JwtTokenProvider jwtTokenProvider;

    private static final String REFRESH_TOKEN_PREFIX = "RT:";

    public void saveRefreshToken(Long memberId, String refreshToken) {
        Long expirationMillis = jwtTokenProvider.getExpiration(refreshToken);
        if (expirationMillis == null) {
            throw new TempHandler(ErrorStatus.INVALID_TOKEN);
        }
        long nowMillis = System.currentTimeMillis();
        long durationMillis = expirationMillis - nowMillis;
        String key = REFRESH_TOKEN_PREFIX + memberId;
        redisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(durationMillis));
    }

    public String getRefreshToken(Long memberId) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteRefreshToken(Long memberId) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        redisTemplate.delete(key);
    }
}
