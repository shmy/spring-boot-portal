package tech.shmy.portal.infrastructure;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;
import tech.shmy.portal.application.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class GlobalConfigurer implements WebMvcConfigurer {
    @Autowired
    AuthService authService;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new I18nInterceptor());
        registry.addInterceptor(new JWTInterceptor(authService)).addPathPatterns("/api/**");
    }

    public static class I18nInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String acceptLanguage = request.getHeader("Accept-Language");
            String firstLanguage = "";
            if (acceptLanguage != null) {
                firstLanguage = acceptLanguage.split(",")[0];
                firstLanguage = firstLanguage.trim();
            }
            if (Strings.isNotEmpty(firstLanguage)) {
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                if (localeResolver == null) {
                    throw new IllegalStateException(
                            "No LocaleResolver found: not in a DispatcherServlet request?");
                }
                localeResolver.setLocale(request, response, StringUtils.parseLocale(firstLanguage));
            }
            return super.preHandle(request, response, handler);
        }
    }
    public static class JWTInterceptor extends HandlerInterceptorAdapter {
        AuthService authService;
        public JWTInterceptor(AuthService authService) {
            this.authService = authService;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            String token = getToken(request);
            if (token == null) {
                throw new Exception("Token 未携带");
            }
            Long id = authService.validateToken(token);
            if (id == null) {
                throw new Exception("Token 无效");
            }
            System.out.println(id);
            return true;
//            return super.preHandle(request, response, handler);
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

}
