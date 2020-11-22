package tech.shmy.portal.application.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data()
@TableName(value = "user")
public class User {
    @TableId(type = IdType.AUTO)
    private String id;
    private String username;
    @JsonIgnore
    private String password;
    private String avatar;
    private String phone;
    private boolean enabled;
    private Date createdAt;
    private Date updatedAt;
}
