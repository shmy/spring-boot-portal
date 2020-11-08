package tech.shmy.portal.application.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.service.AuthService;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("login")
    public ResultBean<LoginResult> login(@RequestBody Long id) {
        String token = authService.generateToken(id);
        int validity = AuthService.getValidity();
        return ResultBean.success(new LoginResult(token, validity));
    }
    @Data
    @AllArgsConstructor
    public static class LoginResult {
        private String token;
        private int validity;
    }
}
