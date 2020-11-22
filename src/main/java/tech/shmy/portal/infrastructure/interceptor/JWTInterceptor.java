package tech.shmy.portal.infrastructure.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tech.shmy.portal.application.domain.entity.Token;
import tech.shmy.portal.application.domain.entity.User;
import tech.shmy.portal.application.domain.repository.UserRepository;
import tech.shmy.portal.application.service.AuthService;
import tech.shmy.portal.application.service.LocaleService;
import tech.shmy.portal.application.service.impl.CombineAuthCacheServiceImpl;
import tech.shmy.portal.infrastructure.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Slf4j
@Component
public class JWTInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CombineAuthCacheServiceImpl combineAuthCacheService;
    @Autowired
    private LocaleService localeService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = getToken(request);
        if (token == null) {
            throw new Exception(localeService.get("auth.token.required"));
        }
        String id = authService.validateToken(token);
        if (id == null) {
            throw new Exception(localeService.get("auth.token.invalid"));
        }
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new Exception(localeService.get("auth.user.not_exist"));
        }
        User user = userOptional.get();
        String dbToken = combineAuthCacheService.getToken(user.getId(), Token.TokenType.WEB);
        if (dbToken == null || !dbToken.equals(token)) {
            throw new Exception(localeService.get("auth.token.recycled"));
        }
        request.setAttribute(Constant.AUTH_USER_KEY, user);
        request.setAttribute(Constant.AUTH_USER_PERMISSION_KEY, combineAuthCacheService.getPermissions(user.getId()));
        return super.preHandle(request, response, handler);
    }

    private String getToken(HttpServletRequest request) {
        String token = parseTokenFromHeader(request);
        if (token == null) {
            token = parseTokenFromQueryString(request);
        }
        return token;
    }

    private String parseTokenFromHeader(HttpServletRequest request) {
        String token = null;
        String authorizationContent = request.getHeader(Constant.HEADER_AUTHORIZATION_KEY);
        if (authorizationContent != null) {
            String[] intersected = authorizationContent.split(" ");
            token = intersected.length > 1 ? intersected[1] : null;
        }
        return token;
    }

    private String parseTokenFromQueryString(HttpServletRequest request) {
        return request.getParameter(Constant.QUERY_STRING_AUTHORIZATION_KEY);
    }
}
