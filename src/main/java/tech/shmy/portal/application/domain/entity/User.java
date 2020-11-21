package tech.shmy.portal.application.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data(staticConstructor = "of")
@TableName(value = "user")
public class User {
    @TableId
    private String id;
    private String username;
    private String password;
    private String avatar;
    private String phone;
    private boolean enabled;
    private Date created_at;
    private Date updated_at;
}
