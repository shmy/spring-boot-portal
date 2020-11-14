package tech.shmy.portal.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Service
public class LocaleService {
    @Autowired
    private MessageSource messageSource;

    public String get(String msgKey) {
        return getWithArgs(msgKey, null);
    }
    public String get(String msgKey, @Nullable Object[] args) {
        return getWithArgs(msgKey, args);
    }
    private String getWithArgs(String msgKey, @Nullable Object[] args) {
        try {
            return messageSource.getMessage(msgKey, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            e.printStackTrace();
            return msgKey;
        }
    }
}
