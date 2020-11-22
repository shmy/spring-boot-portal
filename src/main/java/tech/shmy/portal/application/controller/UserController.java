package tech.shmy.portal.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.domain.Perm;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.User;
import tech.shmy.portal.application.interfaces.impl.RestControllerDelegateImpl;
import tech.shmy.portal.application.service.UserService;
import tech.shmy.portal.infrastructure.annotation.PermissionCheck;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final RestControllerDelegateImpl<User> delegate;

    @Autowired
    public UserController(UserService userService, RestControllerDelegateImpl<User> delegate) {
        this.delegate = delegate;
        this.delegate.setService(userService);
    }

    @PermissionCheck({Perm.User.DETAIL})
    @GetMapping("")
    public ResultBean<List<User>> list() {
        return delegate.list();
    }

    @PermissionCheck(Perm.User.DETAIL)
    @GetMapping("{id}")
    public ResultBean<User> detail(@PathVariable String id) {
        return delegate.detail(id);
    }

    @PermissionCheck(Perm.User.CREATE)
    @PostMapping("")
    public ResultBean<User> create(@RequestBody User data) {
        return delegate.create(data);
    }

    @PermissionCheck(Perm.User.UPDATE)
    @PutMapping("{id}")
    public ResultBean<User> update(@PathVariable String id, @RequestBody User data) {
        return delegate.update(id, data);
    }

    @DeleteMapping("{id}")
    @PermissionCheck(Perm.User.DELETE)
    public ResultBean<User> delete(@PathVariable String id) {
        return delegate.delete(id);
    }
}
