package tech.shmy.portal.application.interfaces;

import tech.shmy.portal.application.domain.entity.Token;

import java.util.List;

public interface IAuthCacheService {
    String getToken(String userId, Token.TokenType type);
    void setToken(String userId, Token.TokenType type, String token);
    List<String> getPermissions(String userId);
}
