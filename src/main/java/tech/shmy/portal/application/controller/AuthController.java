package tech.shmy.portal.application.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.portal.application.domain.ResultBean;
import tech.shmy.portal.application.domain.User;
import tech.shmy.portal.application.service.AuthService;
import tech.shmy.portal.application.service.UserService;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;
    @PostMapping("login")
    public ResultBean<LoginResult> login(@RequestBody User user) {
        User user1 = userService.login(user.getUsername(), user.getPassword());
        if (user1 == null) {
            return ResultBean.error("用户名或密码错误");
        }
        String token = authService.generateToken(user1.getId());
        user1.setToken(token);
        userService.updateById(user1);
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
