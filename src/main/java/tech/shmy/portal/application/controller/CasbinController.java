package tech.shmy.portal.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.domain.CasbinRule;
import tech.shmy.portal.application.service.CasbinService;

import java.util.List;

@RestController
@RequestMapping("/api/casbin")
public class CasbinController {
    @Autowired
    private CasbinService casbinService;

    @GetMapping("")
    public List<String> getAllRoles() {
        return casbinService.getAllRoles();
    }

    @PostMapping("")
    public CasbinRule addRoleWithPermission(@RequestBody CasbinService.Policy policy) throws Exception {
        return casbinService.addRoleWithPermission(policy);
    }
    @PostMapping("delete")
    public CasbinRule removePermissionForRole(@RequestBody CasbinService.Policy policy) throws Exception {
        return casbinService.removePermissionForRole(policy);
    }
//    public List<String> getPermission() {}
}
