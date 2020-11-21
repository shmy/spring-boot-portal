package tech.shmy.portal.infrastructure.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tech.shmy.portal.application.domain.entity.Token;
import tech.shmy.portal.application.domain.entity.User;
import tech.shmy.portal.application.service.AuthService;
import tech.shmy.portal.application.service.LocaleService;
import tech.shmy.portal.application.service.UserService;
import tech.shmy.portal.application.service.impl.CombineAuthCacheServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class JWTInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;
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
        User user = userService.getById(id);
        if (user == null) {
            throw new Exception(localeService.get("auth.user.not_exist"));
        }
        String dbToken = authService.getTokenFromDB(user.getId(), Token.TokenType.WEB);
        if (dbToken == null || !dbToken.equals(token)) {
            throw new Exception(localeService.get("auth.token.recycled"));
        }
        request.setAttribute("authUser", user);
        request.setAttribute("authUserPermissions", combineAuthCacheService.getPermissions(user.getId()));
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
        String authorizationContent = request.getHeader("Authorization");
        if (authorizationContent != null) {
            String[] splitted = authorizationContent.split(" ");
            token = splitted.length > 1 ? splitted[1] : null;
        }
        return token;
    }

    private String parseTokenFromQueryString(HttpServletRequest request) {
        return request.getParameter("token");
    }
}
