package tech.shmy.portal.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.shmy.portal.application.domain.entity.AssociatedRoleUser;

public interface AssociatedRoleUserRepository extends JpaRepository<AssociatedRoleUser, String> {
}
