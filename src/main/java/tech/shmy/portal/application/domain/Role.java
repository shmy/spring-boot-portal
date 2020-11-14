package tech.shmy.portal.application.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "role", schema = "public")
public class Role {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private String intro;
}