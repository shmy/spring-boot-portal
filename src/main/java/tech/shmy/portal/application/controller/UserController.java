package tech.shmy.portal.application.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.domain.Perm;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.Permission;
import tech.shmy.portal.application.domain.entity.Role;
import tech.shmy.portal.application.domain.entity.User;
import tech.shmy.portal.application.domain.repository.PermissionRepository;
import tech.shmy.portal.application.domain.repository.RoleRepository;
import tech.shmy.portal.application.domain.repository.UserRepository;
import tech.shmy.portal.application.interfaces.impl.RestControllerImpl;
import tech.shmy.portal.infrastructure.annotation.PermissionCheck;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController extends RestControllerImpl<User, String> {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public JpaRepository<User, String> getRepository() {
        return userRepository;
    }

    @PermissionCheck({Perm.User.DETAIL})
    @GetMapping("")
    public ResultBean<List<User>> list() {
        return super.list();
    }

    @PermissionCheck(Perm.User.DETAIL)
    @GetMapping("{id}")
    public ResultBean<User> detail(@PathVariable String id) throws Exception {
        return super.detail(id);
    }

    @PermissionCheck(Perm.User.CREATE)
    @PostMapping("")
    public ResultBean<User> create(@RequestBody User data) {
        return super.create(data);
    }

    @PermissionCheck(Perm.User.UPDATE)
    @PutMapping("{id}")
    public ResultBean<User> update(@PathVariable String id, @RequestBody User data) throws Exception {
        return super.update(id, data);
    }

    @DeleteMapping("{id}")
    @PermissionCheck(Perm.User.DELETE)
    public ResultBean<User> delete(@PathVariable String id) throws Exception {
        return super.delete(id);
    }

    @GetMapping("{id}/roles")
    @PermissionCheck(Perm.User.DETAIL)
    public ResultBean<List<Role>> getRoles(@PathVariable String id) {
        return ResultBean.success(roleRepository.getRolesByUser(id));
    }

    @GetMapping("{id}/permissions")
    @PermissionCheck(Perm.User.DETAIL)
    public ResultBean<List<Permission>> getPermissions(@PathVariable String id) {
        return ResultBean.success(permissionRepository.getPermissionsByUser(id));
    }
}
