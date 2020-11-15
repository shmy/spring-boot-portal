package tech.shmy.portal.application.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.casbin.jcasbin.main.Enforcer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.Permission;
import tech.shmy.portal.application.domain.Role;

import java.util.ArrayList;
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
        HashMap<String, Boolean> includesMap = getSelectedMap(selected);
        hashMap.put("includes", includesMap);
        hashMap.put("roles", roles);
        return hashMap;
    }

    public HashMap<String, Object> getPermsByRole(String roleId) {
        List<String> selected = mapToPermList(enforcer
                .getFilteredPolicy(0, ROLE_PREFIX + roleId));
        Permission[] permissions = Permission.values();
        List<ParsedPerm> perms = parsePerms(permissions);
        HashMap<String, Object> hashMap = new HashMap<>();
        HashMap<String, Boolean> includesMap = getSelectedMap(selected);
        hashMap.put("includes", includesMap);
        hashMap.put("perms", perms);
        return hashMap;
    }

    public HashMap<String, Boolean> getPermsByUser(String userId) {
        List<String> perms = new ArrayList<>();
        try {
            perms = mapToPermList(enforcer
                    .getImplicitPermissionsForUser(USER_PREFIX + userId));
        } catch (IllegalArgumentException ignored) {
        }
        return getSelectedMap(perms);
    }

    private HashMap<String, Boolean> getSelectedMap(List<String> list) {
        HashMap<String, Boolean> selectedMap = new HashMap<>();
        list.forEach(item -> {
            selectedMap.put(item, true);
        });
        return selectedMap;
    }
    private List<String> mapToPermList(List<List<String>> input) {
        return input
                .stream()
                .map((List<String> row) -> row.get(1) + "." + row.get(2))
                .distinct()
                .collect(Collectors.toList());
    }
    private List<ParsedPerm> parsePerms(Permission[] permissions) {
        HashMap<String, ArrayList<Permission>> grouped = new HashMap<>();
        for (Permission permission : permissions) {
            String object = permission.getObject();
            if (grouped.containsKey(object)) {
                grouped.get(object).add(permission);
            } else {
                ArrayList<Permission> list = new ArrayList<>();
                list.add(permission);
                grouped.put(object, list);
            }
        }
        return grouped.keySet().stream().map(key -> {
            ParsedPerm parent = new ParsedPerm();
            ArrayList<Permission> item = grouped.get(key);
            parent.setName(item.get(0).getObjectName());
            parent.setDescription(item.get(0).getObjectDescription());
            parent.setPerms(item.stream().map(curr -> {
                ParsedPerm child = new ParsedPerm();
                child.setName(localeService.get(curr.getActionName()));
                child.setDescription(localeService.get(curr.getActionDescription()));
                child.setCode(curr.getObject() + "." + curr.getAction());
                return child;
            }).collect(Collectors.toList()));
            return parent;
        }).collect(Collectors.toList());
    }
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ParsedPerm {
        private String name;
        private String description;
        @Nullable
        private String code;
        @Nullable
        private List<ParsedPerm> perms;
    }
}
