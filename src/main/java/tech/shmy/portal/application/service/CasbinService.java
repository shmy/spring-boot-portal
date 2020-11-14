package tech.shmy.portal.application.service;

import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.Permission;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CasbinService {
    public static String USER_PREFIX = "user::";
    public static String ROLE_PREFIX = "role::";
    @Autowired
    private Enforcer enforcer;
    public boolean checkPermission(String userId, Permission[] permissions) {
        for (Permission permission : permissions) {
            if (!enforcer.enforce(USER_PREFIX + userId, permission.getObject(), permission.getAction())) {
                return false;
            }
        }
        return true;
    }
    public List<String> saveUserRoleAssociation(String userId, List<String> roleIds) {
        String subject = USER_PREFIX + userId;
        enforcer.removeFilteredGroupingPolicy(0, subject);
        roleIds.forEach(roleId -> {
            roleId = ROLE_PREFIX + roleId;
            enforcer.addGroupingPolicy(subject, roleId);
        });
        return roleIds;
    }

    public List<String> saveRolePermAssociation(String roleId, List<String> permCodes) {
        enforcer.removeFilteredPolicy(0, ROLE_PREFIX + roleId);
        permCodes.forEach(permCode -> {
            String[] codes = permCode.split("\\.");
            String subject = ROLE_PREFIX + roleId;
            String object = codes[0];
            String action = codes[1];
            enforcer.addPolicy(subject, object, action);
        });
        return getPermissionsByRole(roleId);
    }

    public List<String> getPermissionsByUser(String userId) {
        try {
            return mapToPermissionList(enforcer
                    .getImplicitPermissionsForUser(USER_PREFIX + userId));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<String> getPermissionsByRole(String roleId) {
        return mapToPermissionList(enforcer
                .getFilteredPolicy(0, ROLE_PREFIX + roleId));
    }

    private List<String> mapToPermissionList(List<List<String>> input) {
        return input
                .stream()
                .map((List<String> row) -> row.get(1) + "." + row.get(2))
                .distinct()
                .collect(Collectors.toList());
    }
}
