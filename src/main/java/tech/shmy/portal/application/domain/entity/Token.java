package tech.shmy.portal.application.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data()
@Entity(name = "token")
public class Token extends IEntity<String>  {
    private TokenType type;
    private String token;
    private String userId;
    public enum TokenType {
        WEB(),
        ANDROID(),
        IOS(),
    }
}
