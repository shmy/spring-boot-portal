package tech.shmy.portal.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.Token;
import tech.shmy.portal.application.interfaces.IAuthCacheService;
import tech.shmy.portal.application.service.RedisService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisAuthCacheServiceImpl implements IAuthCacheService {
    private static final long TOKEN_CACHE_TIME = 1;
    private static final long PERMISSION_CACHE_TIME = 1;
    private static final TimeUnit TIME_UNIT = TimeUnit.MINUTES;
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
        redisService.set(key, token, TOKEN_CACHE_TIME, TIME_UNIT);
        log.info("Do set token to Redis: userId={}, type={}, token={}", userId, type, token);
    }

    @Override
    public List<String> getPermissions(String userId) {
        String key = parsePermissionKey(userId);
        if (redisService.hasKey(key)) {
            List<String> permissions = (List<String>) redisService.get(key);
            log.info("Got permissions from Redis: userId={}, permissions={}", userId, permissions);
            return permissions;
        }
        return null;
    }

    public void setPermissions(String userId, List<String> permissions) {
        String key = parsePermissionKey(userId);
        redisService.set(key, permissions, PERMISSION_CACHE_TIME, TIME_UNIT);
        log.info("Do set permissions to Redis: userId={}, permissions={}", userId, permissions);

    }

    private String parseTokenKey(String userId, Token.TokenType type) {
        return "portal:token:" + userId + ":" + type.name();
    }

    private String parsePermissionKey(String userId) {
        return "portal:permission:" + userId;
    }
}
