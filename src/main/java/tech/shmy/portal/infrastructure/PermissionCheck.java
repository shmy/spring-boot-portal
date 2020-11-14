package tech.shmy.portal.infrastructure;

import tech.shmy.portal.application.domain.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PermissionCheck {
    Permission[] value();
}
