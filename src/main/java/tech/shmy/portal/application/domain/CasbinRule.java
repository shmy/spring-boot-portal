package tech.shmy.portal.application.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
@TableName(value = "casbin_rule", schema = "public")
public class CasbinRule {
    @TableField(value = "ptype")
    private String ptype;
    private String v0;
    private String v1;
    private String v2;
    private String v3;
    private String v4;
    private String v5;

    @Mapper
    public interface CasbinRuleMapper extends BaseMapper<CasbinRule> {

    }
}