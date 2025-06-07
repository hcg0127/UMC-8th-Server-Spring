package umc.spring.repository.redisRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import umc.spring.config.security.jwt.JwtTokenProvider;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class LogoutAccessTokenRepository {

    private final StringRedisTemplate stringRedisTemplate;

    private final JwtTokenProvider jwtTokenProvider;

    private static final String LOGOUT_ACCESS_TOKEN_PREFIX = "Logout:";

    public void saveLogoutAccessToken(Long memberId, String accessToken) {
        long expirationMillis = jwtTokenProvider.getExpiration(accessToken);
        long nowMillis = System.currentTimeMillis();
        long durationMillis = expirationMillis - nowMillis;
        String key = LOGOUT_ACCESS_TOKEN_PREFIX + memberId;
        stringRedisTemplate.opsForValue().set(key, accessToken, Duration.ofMillis(durationMillis));
    }

    public boolean findLogoutAccessToken(Long memberId) {
        String key = LOGOUT_ACCESS_TOKEN_PREFIX + memberId;
        return stringRedisTemplate.hasKey(key);
    }

    public String getLogoutAccessToken(Long memberId) {
        String key = LOGOUT_ACCESS_TOKEN_PREFIX + memberId;
        return stringRedisTemplate.opsForValue().get(key);
    }

    public void deleteLogoutAccessToken(Long memberId) {
        String key = LOGOUT_ACCESS_TOKEN_PREFIX + memberId;
        stringRedisTemplate.delete(key);
    }
}
