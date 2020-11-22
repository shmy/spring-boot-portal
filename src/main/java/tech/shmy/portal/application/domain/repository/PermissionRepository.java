package tech.shmy.portal.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.shmy.portal.application.domain.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, String> {
}
