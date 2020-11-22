package tech.shmy.portal.application.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.Token;
import tech.shmy.portal.application.domain.repository.TokenRepository;
import tech.shmy.portal.application.domain.repository.UserRepository;
import tech.shmy.portal.application.interfaces.IAuthCacheService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MysqlAuthCacheServiceImpl implements IAuthCacheService {
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public String getToken(String userId, Token.TokenType type) {
        Optional<Token> optionalToken = tokenRepository.findByUserIdAndType(userId, type);
        if (optionalToken.isPresent()) {
            Token tokenEntity = optionalToken.get();
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
        tokenEntity.setUserId(userId);
        Optional<Token> optionalToken = tokenRepository.findByUserIdAndType(userId, type);
        optionalToken.ifPresent(value -> {
            tokenEntity.setId(value.getId());
            tokenEntity.setCreatedAt(value.getCreatedAt());
            tokenEntity.setUpdatedAt(value.getUpdatedAt());
        });
        tokenRepository.save(tokenEntity);
        log.info("Set token to Mysql: userId={}, type={}, token={}", userId, type, token);

    }

    @Override
    public List<String> getPermissions(String userId) {
        List<String> permissions = userRepository.getPermissionsByUser(userId);
        log.info("Get permissions from Mysql: userId={}, permissions={}", userId, permissions);
        return permissions;
    }

    @Override
    public void delToken(String userId, Token.TokenType type) {
        // TODO: 避免主键增长过快，应该update token 为 null, 而不是删除
        tokenRepository.deleteByUserIdAndType(userId, type);
        log.info("Del token from Mysql: userId={}, type={}", userId, type);
    }

    @Override
    public void delPermissions(String userId) {

    }

}
