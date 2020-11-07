package tech.shmy.portal.infrastructure;

import org.apache.logging.log4j.util.Strings;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class GlobalConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new I18nInterceptor());
//        registry.addInterceptor(new PermissionInterceptor(enforcer)).addPathPatterns("/api/**");
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

}
