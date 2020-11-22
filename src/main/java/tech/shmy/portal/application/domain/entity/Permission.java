package tech.shmy.portal.application.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data()
@Entity(name = "permission")
public class Permission extends IEntity<String> {
    private String name;
    private String description;
    private String code;
    private boolean enabled;
}
