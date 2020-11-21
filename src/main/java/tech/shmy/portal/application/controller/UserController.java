package tech.shmy.portal.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.infrastructure.annotation.AuthUser;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.User;
import tech.shmy.portal.application.interfaces.impl.RestControllerImpl;
import tech.shmy.portal.application.service.UserService;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController extends RestControllerImpl<User> {
    @Autowired
    UserService service;
    @Override
    public UserService getService() {
        return service;
    }

    @GetMapping("{id}/test")
    public ResultBean<User> test(@PathVariable String id, @AuthUser User user) {
        log.info("do get authUser: {}", user);
        return ResultBean.success(getService().getById(id));
    }
}
