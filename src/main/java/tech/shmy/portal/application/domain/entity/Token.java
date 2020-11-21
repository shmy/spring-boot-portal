package tech.shmy.portal.application.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data()
@TableName(value = "token")
public class Token {
    @TableId(type = IdType.AUTO)
    private String id;
    private TokenType type;
    private String token;
    private String user_id;
    private Date created_at;
    private Date updated_at;
    public enum TokenType {
        WEB(),
        ANDROID(),
        IOS(),
    }
}
