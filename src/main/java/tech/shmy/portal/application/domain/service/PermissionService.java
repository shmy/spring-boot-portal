package tech.shmy.portal.application.domain.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.Permission;
import tech.shmy.portal.application.domain.mapper.PermissionMapper;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, Permission> {

}
