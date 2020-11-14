package tech.shmy.portal.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.service.CasbinService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/casbin")
public class CasbinController {
    @Autowired
    private CasbinService casbinService;

    @PostMapping("{id}/setRolesByUser")
    public List<String> setRolesByUser(@PathVariable("id") String userId, @RequestBody List<String> roleIds) {
        return casbinService.setRolesByUser(userId, roleIds);
    }

    @PostMapping("{id}/setPermsByRole")
    public HashMap<String, Object> setPermsByRole(@PathVariable("id") String roleId, @RequestBody List<String> permCodes) {
        return casbinService.setPermsByRole(roleId, permCodes);
    }

    @GetMapping("{id}/getRolesByUser")
    public HashMap<String, Object> getRolesByUser(@PathVariable("id") String userId) {
        return casbinService.getRolesByUser(userId);
    }

    @GetMapping("{id}/getPermsByRole")
    public HashMap<String, Object> getPermsByRole(@PathVariable("id") String roleId) {
        return casbinService.getPermsByRole(roleId);
    }

    @GetMapping("{id}/getPermsByUser")
    public List<String> getPermsByUser(@PathVariable("id") String userId) {
        return casbinService.getPermsByUser(userId);
    }
}
