package tech.shmy.portal.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.domain.Perm;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.Permission;
import tech.shmy.portal.application.domain.entity.Role;
import tech.shmy.portal.application.domain.service.RoleService;
import tech.shmy.portal.application.interfaces.impl.RestControllerDelegateImpl;
import tech.shmy.portal.infrastructure.annotation.PermissionCheck;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RestControllerDelegateImpl<Role> delegate;
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService, RestControllerDelegateImpl<Role> delegate) {
        this.roleService = roleService;
        this.delegate = delegate;
        this.delegate.setService(roleService);
    }

    @PermissionCheck({Perm.Role.DETAIL})
    @GetMapping("")
    public ResultBean<List<Role>> list() {
        return delegate.list();
    }

    @PermissionCheck(Perm.Role.DETAIL)
    @GetMapping("{id}")
    public ResultBean<Role> detail(@PathVariable String id) {
        return delegate.detail(id);
    }

    @PermissionCheck(Perm.Role.CREATE)
    @PostMapping("")
    public ResultBean<Role> create(@RequestBody Role data) {
        return delegate.create(data);
    }

    @PermissionCheck(Perm.Role.UPDATE)
    @PutMapping("{id}")
    public ResultBean<Role> update(@PathVariable String id, @RequestBody Role data) {
        return delegate.update(id, data);
    }

    @PermissionCheck(Perm.Role.DELETE)
    @DeleteMapping("{id}")
    public ResultBean<Role> delete(@PathVariable String id) {
        return delegate.delete(id);
    }

    @PermissionCheck(Perm.User.DETAIL)
    @GetMapping("{id}/permissions")
    public ResultBean<List<Permission>> permissions(@PathVariable String id) {
        List<Permission> permissions = roleService.getPermissions(id);
        return ResultBean.success(permissions);
    }
}
