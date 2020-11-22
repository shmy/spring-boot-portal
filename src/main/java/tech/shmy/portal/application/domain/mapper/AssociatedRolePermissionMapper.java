package tech.shmy.portal.application.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import tech.shmy.portal.application.domain.entity.AssociatedRolePermission;

@Mapper
@Component
public interface AssociatedRolePermissionMapper extends BaseMapper<AssociatedRolePermission> {

}
