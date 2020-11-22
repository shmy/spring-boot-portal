package tech.shmy.portal.infrastructure.resolver;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

public class CustomLocalResolver implements LocaleResolver {
    private static final String LANGUAGE_KEY = "Language";
    private final Locale defaultLocale;

    public CustomLocalResolver(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String langValue = request.getHeader(LANGUAGE_KEY);
        if (langValue == null) {
            return defaultLocale;
        }
        return StringUtils.parseLocale(langValue);
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
    }
}
