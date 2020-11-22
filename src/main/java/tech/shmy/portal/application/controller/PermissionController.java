package tech.shmy.portal.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.portal.application.domain.Perm;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.Permission;
import tech.shmy.portal.application.domain.repository.PermissionRepository;
import tech.shmy.portal.application.interfaces.impl.RestControllerImpl;
import tech.shmy.portal.infrastructure.annotation.PermissionCheck;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/permissions")
public class PermissionController extends RestControllerImpl<Permission, String> {

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public JpaRepository<Permission, String> getRepository() {
        return permissionRepository;
    }

    @PermissionCheck({Perm.Permission.DETAIL})
    @GetMapping("")
    public ResultBean<List<Permission>> list() {
        return super.list();
    }

}
