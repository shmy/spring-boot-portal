package tech.shmy.portal.application.domain.pojo.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
