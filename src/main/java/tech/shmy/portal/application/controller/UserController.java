package tech.shmy.portal.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.User;
import tech.shmy.portal.application.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController extends RestControllerImpl<User> {
    @Autowired
    UserService service;
    @Override
    public UserService getService() {
        return service;
    }

    @Override
    @PreAuthorize("hasAuthority('user.list')")
    public ResultBean<List<User>> list() {
        return super.list();
    }
}
