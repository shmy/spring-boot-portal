package tech.shmy.portal.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.shmy.portal.application.domain.entity.Token;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByUserIdAndType(String userId, Token.TokenType type);
    void deleteByUserIdAndType(String userId, Token.TokenType type);
}
