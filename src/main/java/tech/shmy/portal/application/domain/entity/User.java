package tech.shmy.portal.application.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data()
@Entity(name = "user")
public class User extends IEntity<String> {
    @JsonIgnore
    private String username;
    private String password;
    private String avatar;
    private String phone;
    private boolean enabled;
}
