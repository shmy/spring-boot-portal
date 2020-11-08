package tech.shmy.portal.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.portal.application.domain.User;
import tech.shmy.portal.application.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController extends RestControllerImpl<User> {
    @Autowired
    UserService service;

    @Override
    public UserService getService() {
        return service;
    }
}
