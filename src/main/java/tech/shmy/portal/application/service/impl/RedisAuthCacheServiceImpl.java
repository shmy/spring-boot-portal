package tech.shmy.portal.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.Token;
import tech.shmy.portal.application.interfaces.IAuthCacheService;
import tech.shmy.portal.application.service.RedisService;
import tech.shmy.portal.infrastructure.Constant;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisAuthCacheServiceImpl implements IAuthCacheService {
    private static final long TOKEN_CACHE_TIME = 6;
    private static final long PERMISSION_CACHE_TIME = 6;
    private static final TimeUnit TIME_UNIT = TimeUnit.HOURS;
    private static final String TOKEN_PREFIX = "token:";
    private static final String PERMISSION_PREFIX = "permission:";
    @Autowired
    private RedisService redisService;

    @Override
    public String getToken(String userId, Token.TokenType type) {
        String key = parseTokenKey(userId, type);
        if (redisService.hasKey(key)) {
            String token = (String) redisService.get(key);
            log.info("Get token from Redis: userId={}, type={}, token={}", userId, type, token);
            return token;
        }
        return null;
    }

    @Override
    public void setToken(String userId, Token.TokenType type, String token) {
        String key = parseTokenKey(userId, type);
        redisService.set(key, token, TOKEN_CACHE_TIME, TIME_UNIT);
        log.info("Set token to Redis: userId={}, type={}, token={}", userId, type, token);
    }

    @Override
    public List<String> getPermissions(String userId) {
        String key = parsePermissionKey(userId);
        if (redisService.hasKey(key)) {
            List<String> permissions = (List<String>) redisService.get(key);
            log.info("Get permissions from Redis: userId={}, permissions={}", userId, permissions);
            return permissions;
        }
        return null;
    }

    @Override
    public void delToken(String userId, Token.TokenType type) {
        String key = parseTokenKey(userId, type);
        redisService.delete(key);
        log.info("Del token from Redis: userId={}, type={}", userId, type);

    }

    @Override
    public void delPermissions(String userId) {
        String key = parsePermissionKey(userId);
        redisService.delete(key);
        log.info("Del permissions from Redis: userId={}", userId);

    }

    public void setPermissions(String userId, List<String> permissions) {
        String key = parsePermissionKey(userId);
        redisService.set(key, permissions, PERMISSION_CACHE_TIME, TIME_UNIT);
        log.info("Set permissions to Redis: userId={}, permissions={}", userId, permissions);

    }

    private String parseTokenKey(String userId, Token.TokenType type) {
        return Constant.REDIS_KEY_PREFIX + TOKEN_PREFIX + userId + ":" + type.name();
    }

    private String parsePermissionKey(String userId) {
        return Constant.REDIS_KEY_PREFIX + PERMISSION_PREFIX + userId;
    }
}
