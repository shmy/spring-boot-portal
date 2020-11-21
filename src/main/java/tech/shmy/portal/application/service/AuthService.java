package tech.shmy.portal.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.User;

import java.util.Date;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private LocaleService localeService;
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("mysecret");
    // 过期时间 单位：秒
    private static final int VALIDITY = 60 * 60;
    private static final String ID_KEY = "id";

    private String generateToken(String id) {
        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);
        Date expiresDate = new Date(currentTimeMillis + VALIDITY * 1000);
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
    public LoginResult login(String username, String password) throws Exception {
        QueryWrapper<User> lqw = new QueryWrapper<User>()
                .eq("username", username)
                .eq("password", password);
        User user = userService.getOne(lqw);
        if (user == null) {
            throw new Exception(localeService.get("auth.user.incorrect"));
        }
        if (!user.isEnabled()) {
            throw new Exception(localeService.get("auth.user.disabled"));
        }
        String token = generateToken(user.getId());
        LoginResult loginResult =  new LoginResult();
        loginResult.setToken(token);
        loginResult.setValidity(VALIDITY);
        return loginResult;
    }
    @Data
    public static class LoginResult {
        private String token;
        private int validity;
    }
}
