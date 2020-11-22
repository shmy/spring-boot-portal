package tech.shmy.portal.application.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tech.shmy.portal.application.domain.entity.AssociatedRoleUser;

@Mapper
@Component
public interface AssociatedRoleUserMapper extends BaseMapper<AssociatedRoleUser> {

}
