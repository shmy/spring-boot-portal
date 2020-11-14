package tech.shmy.portal.application.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.Permission;
import tech.shmy.portal.application.domain.Role;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CasbinService {
    public static String USER_PREFIX = "user::";
    public static String ROLE_PREFIX = "role::";
    @Autowired
    private Enforcer enforcer;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private RoleService roleService;

    public boolean checkPermission(String userId, Permission[] permissions) {
        for (Permission permission : permissions) {
            if (!enforcer.enforce(USER_PREFIX + userId, permission.getObject(), permission.getAction())) {
                return false;
            }
        }
        return true;
    }

    public List<String> setRolesByUser(String userId, List<String> roleIds) {
        String subject = USER_PREFIX + userId;
        enforcer.removeFilteredGroupingPolicy(0, subject);
        roleIds.forEach(roleId -> {
            roleId = ROLE_PREFIX + roleId;
            enforcer.addGroupingPolicy(subject, roleId);
        });
        return roleIds;
    }

    public HashMap<String, Object> setPermsByRole(String roleId, List<String> permCodes) {
        enforcer.removeFilteredPolicy(0, ROLE_PREFIX + roleId);
        permCodes.forEach(permCode -> {
            String[] codes = permCode.split("\\.");
            String subject = ROLE_PREFIX + roleId;
            String object = codes[0];
            String action = codes[1];
            enforcer.addPolicy(subject, object, action);
        });
        return getPermsByRole(roleId);
    }

    public HashMap<String, Object> getRolesByUser(String userId) {
        List<String> selected = new ArrayList<>();
        try {
            selected = enforcer.getImplicitRolesForUser(USER_PREFIX + userId).stream()
                    .map(role -> role.replaceAll("^" + ROLE_PREFIX, ""))
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        List<Role> roles = roleService.list();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("selected", selected);
        hashMap.put("roles", roles);
        return hashMap;
    }

    public HashMap<String, Object> getPermsByRole(String roleId) {
        List<String> selected = mapToPermList(enforcer
                .getFilteredPolicy(0, ROLE_PREFIX + roleId));
        List<Perm> perms = getAllPerms();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("selected", selected);
        hashMap.put("perms", perms);
        return hashMap;
    }

    public List<String> getPermsByUser(String userId) {
        try {
            return mapToPermList(enforcer
                    .getImplicitPermissionsForUser(USER_PREFIX + userId));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private List<Perm> getAllPerms() {
        Permission[] permissions = Permission.values();
        return Arrays.stream(permissions).map(permission -> new Perm(
                permission.getObject() + "." + permission.getAction(),
                localeService.get(permission.getObjectDescript()),
                localeService.get(permission.getActionDescript())
        )).collect(Collectors.toList());
    }

    private List<String> mapToPermList(List<List<String>> input) {
        return input
                .stream()
                .map((List<String> row) -> row.get(1) + "." + row.get(2))
                .distinct()
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class Perm {
        private String code;
        private String objectDescription;
        private String actionDescription;
    }
}
