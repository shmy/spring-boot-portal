package tech.shmy.portal.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new MyUserDetail();
    }
    public static class MyUserDetail implements UserDetails {

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> e = new ArrayList<>();
            e.add((GrantedAuthority) () -> "user.list");
            e.add((GrantedAuthority) () -> "role.list");
            return e;
        }

        @Override
        public String getPassword() {
            return "123123";
        }

        @Override
        public String getUsername() {
            return "admin";
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
