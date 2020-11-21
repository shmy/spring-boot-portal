package tech.shmy.portal.application.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data()
@TableName(value = "permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private String description;
    private String code;
    private boolean enabled;
    private Date created_at;
    private Date updated_at;
}
