package tech.shmy.portal.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.shmy.portal.infrastructure.interceptor.PermissionInterceptor;
import tech.shmy.portal.infrastructure.resolver.AuthUserArgumentResolver;
import tech.shmy.portal.infrastructure.interceptor.JWTInterceptor;
import tech.shmy.portal.infrastructure.resolver.CustomLocalResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class GlobalConfigurer implements WebMvcConfigurer {
    @Autowired
    JWTInterceptor jwtInterceptor;
    @Autowired
    PermissionInterceptor permissionInterceptor;
    // 国际化配置
    @Bean
    public CustomLocalResolver localeResolver() {
        return new CustomLocalResolver(Locale.SIMPLIFIED_CHINESE);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/login");
//        registry.addInterceptor(permissionInterceptor)
//                .addPathPatterns("/api/**")
//                .excludePathPatterns("/api/auth/login");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthUserArgumentResolver());
    }
}
