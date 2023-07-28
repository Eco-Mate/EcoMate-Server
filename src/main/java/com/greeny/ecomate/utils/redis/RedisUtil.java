package com.greeny.ecomate.utils.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisUtil {

    private final RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void expire(String key, long seconds) {
        redisTemplate.expire(key, Duration.ofSeconds(seconds));
    }

    public void del(String key) {
        redisTemplate.delete(key);
    }

    public void sAdd(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    public void sRem(String key, Object value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    public Boolean sIsMember(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key, value);
    }

    public Long sSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

}
