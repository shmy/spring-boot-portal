package tech.shmy.portal.application.domain.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data()
@Entity(name = "associated_role_permission")
public class AssociatedRolePermission {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String id;
    private String roleId;
    private String permissionId;
}
