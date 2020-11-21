package tech.shmy.portal.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.portal.application.domain.entity.Role;
import tech.shmy.portal.application.interfaces.impl.RestControllerImpl;
import tech.shmy.portal.application.service.RoleService;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends RestControllerImpl<Role> {
    @Autowired
    RoleService service;

    @Override
    public RoleService getService() {
        return service;
    }

}
