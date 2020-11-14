package tech.shmy.portal.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    private static final Algorithm ALGORITHM = Algorithm.HMAC256("mysecret");
    // 过期时间 单位：秒
    private static final int VALIDITY = 60 * 60;
    private static final String ID_KEY = "id";

    public static int getValidity() {
        return VALIDITY;
    }

    public String generateToken(String id) {
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
}
