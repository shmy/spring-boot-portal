package tech.shmy.portal.application.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.Role;
import tech.shmy.portal.application.domain.mapper.RoleMapper;

@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

}
