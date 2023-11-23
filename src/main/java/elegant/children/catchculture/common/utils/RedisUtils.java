package elegant.children.catchculture.common.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
public class RedisUtils {

    @Value("${jwt.token.expiration}")
    private long tokenExpiration;

    private final RedisTemplate<String, Object> redisTemplate;

    public void setData(String key, String value) {
        redisTemplate.opsForValue().set(key, value, tokenExpiration, TimeUnit.SECONDS);
    }

    public String getData(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }


}
