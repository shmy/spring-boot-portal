package tech.shmy.portal.infrastructure.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import tech.shmy.portal.application.service.AuthService;
import tech.shmy.portal.infrastructure.annotation.PermissionCheck;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        List<String> requiredPermissions = findRequiredPermissions(handlerMethod);
        if (requiredPermissions.size() > 0) {
            List<String> permissions = (List<String>) request.getAttribute("authUserPermissions");
            if (!authService.checkPermission(permissions, requiredPermissions)) {
                throw new Exception("权限不足");
            }
        }
        return super.preHandle(request, response, handler);
    }

    private List<String> findRequiredPermissions(HandlerMethod handlerMethod) {
        List<String> permissionList = new ArrayList<>();
        Class<?> clazz = handlerMethod.getBeanType();
        // 在父类上寻找注解
        PermissionCheck permissionCheckForSuperClass = clazz.getSuperclass().getAnnotation(PermissionCheck.class);
        // 在类上寻找注解
        PermissionCheck permissionCheckForClass = clazz.getAnnotation(PermissionCheck.class);
        // 在方法上寻找注解
        PermissionCheck permissionCheckForMethod = handlerMethod.getMethodAnnotation(PermissionCheck.class);
        if (permissionCheckForSuperClass != null) {
            permissionList.addAll(Arrays.asList(permissionCheckForSuperClass.value()));
        }
        if (permissionCheckForClass != null) {
            permissionList.addAll(Arrays.asList(permissionCheckForClass.value()));
        }
        if (permissionCheckForMethod != null) {
            permissionList.addAll(Arrays.asList(permissionCheckForMethod.value()));
        }
        return permissionList;
    }
}
