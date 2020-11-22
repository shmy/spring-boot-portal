package tech.shmy.portal.application.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data()
@Entity(name = "role")
public class Role extends IEntity<String> {
    private String name;
    private String description;
    private boolean enabled;
}
