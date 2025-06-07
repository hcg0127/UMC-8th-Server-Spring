package umc.spring.repository.redisRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.config.security.jwt.JwtTokenProvider;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final StringRedisTemplate stringRedisTemplate;

    private final JwtTokenProvider jwtTokenProvider;

    private static final String REFRESH_TOKEN_PREFIX = "RT:";

    public void saveRefreshToken(Long memberId, String refreshToken) {
        long expirationMillis = jwtTokenProvider.getExpiration(refreshToken);
        long nowMillis = System.currentTimeMillis();
        long durationMillis = expirationMillis - nowMillis;
        String key = REFRESH_TOKEN_PREFIX + memberId;
        stringRedisTemplate.opsForValue().set(key, refreshToken, Duration.ofMillis(durationMillis));
    }

    public boolean findRefreshToken(Long memberId) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        return stringRedisTemplate.hasKey(key);
    }

    public String getRefreshToken(Long memberId) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void deleteRefreshToken(Long memberId) {
        String key = REFRESH_TOKEN_PREFIX + memberId;
        stringRedisTemplate.delete(key);
    }
}
