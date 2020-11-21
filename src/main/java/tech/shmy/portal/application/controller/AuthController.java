package tech.shmy.portal.application.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.shmy.portal.application.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @PostMapping("login")
    public AuthService.LoginResult login(@RequestBody JsonNode body) throws Exception {
        String username = body.get("username").asText();
        String password = body.get("password").asText();
        return authService.login(username, password);
    }
}
