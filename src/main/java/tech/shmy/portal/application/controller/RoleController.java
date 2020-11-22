package tech.shmy.portal.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.domain.Perm;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.Permission;
import tech.shmy.portal.application.domain.entity.Role;
import tech.shmy.portal.application.domain.repository.PermissionRepository;
import tech.shmy.portal.application.domain.repository.RoleRepository;
import tech.shmy.portal.application.interfaces.impl.RestControllerImpl;
import tech.shmy.portal.infrastructure.annotation.PermissionCheck;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController extends RestControllerImpl<Role, String> {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public JpaRepository<Role, String> getRepository() {
        return roleRepository;
    }

    @PermissionCheck({Perm.Role.DETAIL})
    @GetMapping("")
    public ResultBean<List<Role>> list() {
        return super.list();
    }

    @PermissionCheck(Perm.Role.DETAIL)
    @GetMapping("{id}")
    public ResultBean<Role> detail(@PathVariable String id) throws Exception {
        return super.detail(id);
    }

    @PermissionCheck(Perm.Role.CREATE)
    @PostMapping("")
    public ResultBean<Role> create(@RequestBody Role data) {
        return super.create(data);
    }

    @PermissionCheck(Perm.Role.UPDATE)
    @PutMapping("{id}")
    public ResultBean<Role> update(@PathVariable String id, @RequestBody Role data) throws Exception {
        return super.update(id, data);
    }

    @PermissionCheck(Perm.Role.DELETE)
    @DeleteMapping("{id}")
    public ResultBean<Role> delete(@PathVariable String id) throws Exception {
        return super.delete(id);
    }

    @PermissionCheck(Perm.Role.DETAIL)
    @GetMapping("{id}/permissions")
    public ResultBean<List<Permission>> permissions(@PathVariable String id) {
        List<Permission> permissions = permissionRepository.getPermissionsByRole(id);
        return ResultBean.success(permissions);
    }
}
