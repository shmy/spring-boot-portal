package tech.shmy.portal.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.shmy.portal.application.domain.entity.Permission;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission, String> {
    @Query(value = "SELECT * FROM `permission` WHERE `id` IN (\n" +
            "    SELECT `permission_id` FROM `associated_role_permission` WHERE `role_id` IN (\n" +
            "        SELECT `id` FROM `role` WHERE `id` = :roleId\n" +
            "    )\n" +
            ")", nativeQuery = true)
    List<Permission> getPermissionsByRole(String roleId);
    @Query(value = "SELECT * FROM `permission` WHERE `id` IN (\n" +
            "    SELECT `permission_id` FROM `associated_role_permission` WHERE `role_id` IN (\n" +
            "        SELECT `id` FROM `role` WHERE `id` IN (\n" +
            "            SELECT `role_id` FROM `associated_role_user` WHERE `user_id` = :userId\n" +
            "        )\n" +
            "    )\n" +
            ")", nativeQuery = true)
    List<Permission> getPermissionsByUser(String userId);
}
