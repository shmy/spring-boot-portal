package tech.shmy.portal.application.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data(staticConstructor = "of")
@TableName(value = "role")
public class Role {
    @TableId
    private String id;
    private String name;
    private String description;
    private Date created_at;
    private Date updated_at;
}
