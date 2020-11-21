package tech.shmy.portal.application.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tech.shmy.portal.application.domain.entity.User;

import java.util.List;

@Mapper
@Component
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT `code`\n" +
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
            "            WHERE `user_id` = '${userId}'\n" +
            "        )\n" +
            "    )\n" +
            "      AND `enabled` = 1\n" +
            ")\n" +
            "  AND `enabled` = 1")
    List<String> getPermissionsById(@Param("userId") String userId);
}
