package tech.shmy.portal.application.interfaces;

import tech.shmy.portal.application.domain.entity.Token;

public interface IAuthCacheService {
    String getToken(String userId, Token.TokenType type);
    void setToken(String userId, Token.TokenType type, String token);
}
