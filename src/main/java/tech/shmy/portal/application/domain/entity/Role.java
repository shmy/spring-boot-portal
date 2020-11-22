package tech.shmy.portal.application.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data()
@TableName(value = "role")
public class Role {
    @TableId(type = IdType.AUTO)
    private String id;
    private String name;
    private String description;
    private boolean enabled;
    private Date createdAt;
    private Date updatedAt;
}
