package tech.shmy.portal.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.Token;
import tech.shmy.portal.application.interfaces.IAuthCacheService;
import tech.shmy.portal.application.service.RedisService;

@Slf4j
@Service
public class RedisAuthCacheServiceImpl implements IAuthCacheService {
    @Autowired
    private RedisService redisService;

    @Override
    public String getToken(String userId, Token.TokenType type) {
        String key = parseTokenKey(userId, type);
        if (redisService.hasKey(key)) {
            String token = (String) redisService.get(key);
            log.info("Got token from Redis: userId={}, type={}, token={}", userId, type, token);
            return token;
        }
        return null;
    }

    @Override
    public void setToken(String userId, Token.TokenType type, String token) {
        String key = parseTokenKey(userId, type);
        redisService.set(key, token);
        log.info("Do set token to Redis: userId={}, type={}, token={}", userId, type, token);
    }

    private String parseTokenKey(String userId, Token.TokenType type) {
        return "token:" + userId + ":" + type.name();
    }
}
