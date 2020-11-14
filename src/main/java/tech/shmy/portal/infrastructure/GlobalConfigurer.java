package tech.shmy.portal.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tech.shmy.portal.application.domain.User;
import tech.shmy.portal.application.service.AuthService;
import tech.shmy.portal.application.service.CasbinService;
import tech.shmy.portal.application.service.LocaleService;
import tech.shmy.portal.application.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class GlobalConfigurer implements WebMvcConfigurer {
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;
    @Autowired
    CasbinService casbinService;
    @Autowired
    LocaleService localeService;

    // 国际化配置
    @Bean
    public CustomLocalResolver localeResolver() {
        return new CustomLocalResolver(Locale.SIMPLIFIED_CHINESE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor(authService, userService, localeService)).addPathPatterns("/api/**");
        registry.addInterceptor(new CasbinInterceptor(casbinService)).addPathPatterns("/api/**");
    }

    public static class JWTInterceptor extends HandlerInterceptorAdapter {
        private final AuthService authService;
        private final UserService userService;
        private final LocaleService localeService;

        public JWTInterceptor(AuthService authService, UserService userService, LocaleService localeService) {
            this.authService = authService;
            this.userService = userService;
            this.localeService = localeService;
        }

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
            if (!user.getToken().equals(token)) {
                throw new Exception(localeService.get("auth.token.recycled"));
            }
            request.setAttribute("authUser", user);
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

    public static class CasbinInterceptor extends HandlerInterceptorAdapter {
        private final CasbinService casbinService;

        public CasbinInterceptor(CasbinService casbinService) {
            this.casbinService = casbinService;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            List<PermissionCheck> permissionCheckList = findPermissionCheck(handlerMethod);
            if (permissionCheckList.size() > 0) {
                User user = (User) request.getAttribute("authUser");
                for (PermissionCheck permissionCheck : permissionCheckList) {
                    if (!casbinService.checkPermission(user.getId(), permissionCheck.value())) {
                        throw new Exception("权限不足");
                    }
                }
            }
            return super.preHandle(request, response, handler);
        }

        private List<PermissionCheck> findPermissionCheck(HandlerMethod handlerMethod) {
            List<PermissionCheck> permissionCheckList = new ArrayList<>();
            Class<?> clazz = handlerMethod.getBeanType();
            // 在父类上寻找注解
            PermissionCheck permissionCheckForSuperClass = clazz.getSuperclass().getAnnotation(PermissionCheck.class);
            // 在类上寻找注解
            PermissionCheck permissionCheckForClass = clazz.getAnnotation(PermissionCheck.class);
            // 在方法上寻找注解
            PermissionCheck permissionCheckForMethod = handlerMethod.getMethodAnnotation(PermissionCheck.class);
            if (permissionCheckForSuperClass != null) {
                permissionCheckList.add(permissionCheckForSuperClass);
            }
            if (permissionCheckForClass != null) {
                permissionCheckList.add(permissionCheckForClass);
            }
            if (permissionCheckForMethod != null) {
                permissionCheckList.add(permissionCheckForMethod);
            }
            return permissionCheckList;
        }
    }

    public static class CustomLocalResolver implements LocaleResolver {
        private static final String langKey = "Language";
        private final Locale defaultLocale;

        public CustomLocalResolver(Locale defaultLocale) {
            this.defaultLocale = defaultLocale;
        }

        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            String langValue = request.getHeader(langKey);
            if (langValue == null) {
                return defaultLocale;
            }
            return StringUtils.parseLocale(langValue);
        }

        @Override
        public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        }
    }
}
