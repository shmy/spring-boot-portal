package tech.shmy.portal.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.shmy.portal.application.domain.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, String> {
    @Query(value = "SELECT * FROM `role` WHERE `id` IN (\n" +
            "    SELECT `role_id` FROM `associated_role_user` WHERE `user_id` = :userId\n" +
            ")", nativeQuery = true)
    List<Role> getRolesByUser(String userId);
}
