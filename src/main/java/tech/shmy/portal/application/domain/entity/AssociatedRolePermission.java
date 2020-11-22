package tech.shmy.portal.application.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data()
@TableName(value = "associated_role_permission")
public class AssociatedRolePermission {
    private String roleId;
    private String permissionId;
}
