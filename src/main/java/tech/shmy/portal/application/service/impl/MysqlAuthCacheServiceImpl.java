package tech.shmy.portal.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.Token;
import tech.shmy.portal.application.interfaces.IAuthCacheService;
import tech.shmy.portal.application.service.RedisService;
import tech.shmy.portal.application.service.TokenService;
import tech.shmy.portal.application.service.UserService;

import java.util.List;

@Slf4j
@Service
public class MysqlAuthCacheServiceImpl implements IAuthCacheService {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @Override
    public String getToken(String userId, Token.TokenType type) {
        Token tokenEntity = tokenService.getOne(
                new QueryWrapper<Token>()
                        .eq("user_id", userId)
                        .eq("type", type)
        );
        if (tokenEntity != null) {
            String token = tokenEntity.getToken();
            log.info("Get token from Mysql: userId={}, type={}, token={}", userId, type, token);
            return tokenEntity.getToken();
        }
        return null;
    }

    @Override
    public void setToken(String userId, Token.TokenType type, String token) {
        Token tokenEntity = new Token();
        tokenEntity.setType(type);
        tokenEntity.setToken(token);
        tokenEntity.setUser_id(userId);
        tokenService.saveOrUpdate(tokenEntity, new UpdateWrapper<Token>()
                .eq("type", type)
                .eq("user_id", userId)
        );
        log.info("Set token to Mysql: userId={}, type={}, token={}", userId, type, token);

    }

    @Override
    public List<String> getPermissions(String userId) {
        List<String> permissions = userService.getBaseMapper().getPermissionsById(userId);
        log.info("Get permissions from Mysql: userId={}, permissions={}", userId, permissions);
        return permissions;
    }

    @Override
    public void delToken(String userId, Token.TokenType type) {
        tokenService.remove(new QueryWrapper<Token>().eq("user_id", userId).eq("type", type));
        log.info("Del token from Mysql: userId={}, type={}", userId, type);
    }

    @Override
    public void delPermissions(String userId) {

    }

}
