package tech.shmy.portal.application.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.Token;

@Slf4j
@Service
public class RedisService {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    public void set(String key, Object val) {
        redisTemplate.opsForValue().set(key, val);
    }
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public String parseTokenKey(String userId, Token.TokenType type) {
        return "token:" + userId + ":" + type.name();
    }
}
