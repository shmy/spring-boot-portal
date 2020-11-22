package tech.shmy.portal.application.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.AssociatedRolePermission;
import tech.shmy.portal.application.domain.entity.Permission;
import tech.shmy.portal.application.domain.entity.Role;
import tech.shmy.portal.application.domain.mapper.RoleMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {
    @Autowired
    AssociatedRolePermissionService associatedRolePermissionService;
    @Autowired
    PermissionService permissionService;
    public List<Permission> getPermissions(String roleId) {
        List<AssociatedRolePermission> associatedRolePermissions = associatedRolePermissionService.list(new QueryWrapper<AssociatedRolePermission>().eq("role_id", roleId));
        log.info("{}", associatedRolePermissions);
        List<String> permissionIds = associatedRolePermissions.stream().map(AssociatedRolePermission::getPermissionId).collect(Collectors.toList());
        return permissionService.list(new QueryWrapper<Permission>().in("id", permissionIds));
    }
}
