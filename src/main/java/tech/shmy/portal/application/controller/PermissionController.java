package tech.shmy.portal.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.Permission;
import tech.shmy.portal.application.domain.service.PermissionService;
import tech.shmy.portal.application.interfaces.impl.RestControllerDelegateImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

//    private final PermissionService permissionService;
    private final RestControllerDelegateImpl<Permission> delegate;

    @Autowired
    public PermissionController(PermissionService permissionService, RestControllerDelegateImpl<Permission> delegate) {
//        this.permissionService = permissionService;
        this.delegate = delegate;
        this.delegate.setService(permissionService);
    }

//    @PermissionCheck({Perm.Permission.DETAIL})
    @GetMapping("")
    public ResultBean<List<Permission>> list() {
        // bug??
        return delegate.list();
    }

}
