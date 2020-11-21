package tech.shmy.portal.application.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.Token;
import tech.shmy.portal.application.interfaces.IAuthCacheService;

import java.util.List;

@Service
public class CombineAuthCacheServiceImpl implements IAuthCacheService {
    @Autowired
    private MysqlAuthCacheServiceImpl mysqlAuthCacheService;
    @Autowired
    private RedisAuthCacheServiceImpl redisAuthCacheService;

    @Override
    public String getToken(String userId, Token.TokenType type) {
        String token = redisAuthCacheService.getToken(userId, type);
        // 先从redis取
        if (token != null) {
            return token;
        }
        token = mysqlAuthCacheService.getToken(userId, type);
        // 保存到redis
        if (token != null) {
            redisAuthCacheService.setToken(userId, type, token);
        }
        return token;
    }

    @Override
    public void setToken(String userId, Token.TokenType type, String token) {
        redisAuthCacheService.setToken(userId, type, token);
        mysqlAuthCacheService.setToken(userId, type, token);
    }

    @Override
    public List<String> getPermissions(String userId) {
        List<String> permissions = redisAuthCacheService.getPermissions(userId);
        if (permissions != null) {
            return permissions;
        }
        permissions = mysqlAuthCacheService.getPermissions(userId);
        if (permissions != null) {
            redisAuthCacheService.setPermissions(userId, permissions);
        }
        return permissions;
    }

}
