package tech.shmy.portal.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.service.CasbinService;

import java.util.List;

@RestController
@RequestMapping("/api/casbin")
public class CasbinController {
    @Autowired
    private CasbinService casbinService;

    @PostMapping("{id}/saveUserRoleAssociation")
    public List<String> saveUserRoleAssociation(@PathVariable("id") String userId, @RequestBody List<String> roleIds) {
        return casbinService.saveUserRoleAssociation(userId, roleIds);
    }
    @PostMapping("{id}/saveRolePermAssociation")
    public List<String> saveRolePermAssociation(@PathVariable("id") String roleId, @RequestBody List<String> permCodes) {
        return casbinService.saveRolePermAssociation(roleId, permCodes);
    }
    @GetMapping("{id}/getPermissionsByUser")
    public List<String> getPermissionsByUser(@PathVariable("id") String userId) {
        return casbinService.getPermissionsByUser(userId);
    }
    @GetMapping("{id}/getPermissionsByRole")
    public List<String> getPermissionsByRole(@PathVariable("id") String roleId) {
        return casbinService.getPermissionsByRole(roleId);
    }
}
