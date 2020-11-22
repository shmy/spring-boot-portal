package tech.shmy.portal.application.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@EqualsAndHashCode(callSuper = true)
@Data()
@Entity(name = "token")
public class Token extends IEntity<String>  {
    @Enumerated(EnumType.STRING)
    private TokenType type;
    private String token;
    private String userId;
    public enum TokenType {
        WEB(),
        ANDROID(),
        IOS(),
    }
}
