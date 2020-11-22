package tech.shmy.portal.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.shmy.portal.application.domain.entity.Token;

public interface TokenRepository extends JpaRepository<Token, String> {
    Token findByUserIdAndType(String userId, Token.TokenType type);
}
