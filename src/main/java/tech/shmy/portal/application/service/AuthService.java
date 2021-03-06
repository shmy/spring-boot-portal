package tech.shmy.portal.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.shmy.portal.application.domain.entity.Token;
import tech.shmy.portal.application.domain.entity.User;
import tech.shmy.portal.application.domain.repository.UserRepository;
import tech.shmy.portal.application.service.impl.CombineAuthCacheServiceImpl;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AuthService {
    @Autowired
    private CombineAuthCacheServiceImpl combineAuthCacheService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocaleService localeService;
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("mysecret");
    // 过期时间 单位：秒
    private static final int EXPIRES_IN = 60 * 60;
    private static final String ID_KEY = "id";

    private String generateToken(String id) {
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        Date expiresDate = new Date(currentTimeMillis + EXPIRES_IN * 1000);
        return JWT.create()
                .withClaim(ID_KEY, id)
                .withIssuedAt(now)
                .withExpiresAt(expiresDate)
                .sign(ALGORITHM);
    }

    public String validateToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGORITHM)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(ID_KEY).asString();
        } catch (Exception e) {
            return null;
        }

    }

    public boolean checkPermission(List<String> permissions, List<String> requiredPermissions) {
        for (String p : requiredPermissions) {
            boolean isContains = permissions.contains(p);
            log.info("Require permission: {}, isContains: {}", p, isContains);
            if (!isContains) {
                return false;
            }
        }
        return true;
    }
    public void logout(String userId) {
        combineAuthCacheService.delToken(userId, Token.TokenType.WEB);
        combineAuthCacheService.delPermissions(userId);
    }
    @Transactional
    public LoginResult login(String username, String password) throws Exception {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new Exception(localeService.get("auth.user.incorrect"));
        }
        if (!user.isEnabled()) {
            throw new Exception(localeService.get("auth.user.disabled"));
        }
        Token.TokenType type = Token.TokenType.WEB;
        combineAuthCacheService.delToken(user.getId(), type);
        combineAuthCacheService.delPermissions(user.getId());
        String token = generateToken(user.getId());
        List<String> permissions = combineAuthCacheService.getPermissions(user.getId());
        combineAuthCacheService.setToken(user.getId(), type, token);
        LoginResult loginResult = new LoginResult();
        loginResult.setUser(user);
        loginResult.setToken(token);
        loginResult.setExpiresIn(EXPIRES_IN);
        loginResult.setPermissions(permissions);
        return loginResult;
    }

    @Data
    public static class LoginResult {
        private User user;
        private String token;
        private int expiresIn;
        private List<String> permissions;
    }
}
