package tech.shmy.portal.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.shmy.portal.application.domain.entity.Role;

public interface RoleRepository extends JpaRepository<Role, String> {
}
