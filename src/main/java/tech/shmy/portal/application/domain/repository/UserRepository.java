package tech.shmy.portal.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.shmy.portal.application.domain.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsernameAndPassword(String username, String password);
    @Query(value = "SELECT `code`\n" +
            "FROM `permission`\n" +
            "WHERE `id` IN (\n" +
            "    SELECT `permission_id`\n" +
            "    FROM `associated_role_permission`\n" +
            "    WHERE `role_id` IN (\n" +
            "        SELECT `id`\n" +
            "        FROM `role`\n" +
            "        WHERE `id` IN (\n" +
            "            SELECT `role_id`\n" +
            "            FROM `associated_role_user`\n" +
            "            WHERE `user_id` = :userId\n" +
            "        )\n" +
            "    )\n" +
            "      AND `enabled` = 1\n" +
            ")\n" +
            "  AND `enabled` = 1", nativeQuery = true)
    List<String> getPermissionsById(@Param("userId") String userId);
}
