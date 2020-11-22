package tech.shmy.portal.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.shmy.portal.application.domain.entity.AssociatedRolePermission;

public interface AssociatedRolePermissionRepository extends JpaRepository<AssociatedRolePermission, String> {
}
