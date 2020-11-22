package tech.shmy.portal.application.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.entity.User;
import tech.shmy.portal.application.service.AuthService;
import tech.shmy.portal.infrastructure.annotation.AuthUser;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("login")
    public ResultBean<AuthService.LoginResult> login(@RequestBody JsonNode body) throws Exception {
        String username = body.get("username").asText();
        String password = body.get("password").asText();
        return ResultBean.success(authService.login(username, password));
    }
    @GetMapping("logout")
    public ResultBean<Object> logout(@AuthUser User user) {
        authService.logout(user.getId());
        return ResultBean.success(null);
    }
}
