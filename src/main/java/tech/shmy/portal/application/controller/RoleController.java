package tech.shmy.portal.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.Role;
import tech.shmy.portal.application.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends RestControllerImpl<Role> {
    @Autowired
    RoleService service;

    @Override
    public RoleService getService() {
        return service;
    }

    @Override
    public ResultBean<List<Role>> list() {
        return super.list();
    }
}
