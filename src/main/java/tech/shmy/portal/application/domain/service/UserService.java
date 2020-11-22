package tech.shmy.portal.application.domain.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.AssociatedRoleUser;
import tech.shmy.portal.application.domain.entity.Role;
import tech.shmy.portal.application.domain.entity.User;
import tech.shmy.portal.application.domain.mapper.UserMapper;
import tech.shmy.portal.application.service.impl.CombineAuthCacheServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    @Autowired
    CombineAuthCacheServiceImpl combineAuthCacheService;
    @Autowired
    AssociatedRoleUserService associatedRoleUserService;
    @Autowired
    RoleService roleService;
    public List<Role> getRoles(String userId) {
        List<AssociatedRoleUser> associatedRoleUsers = associatedRoleUserService.list(new QueryWrapper<AssociatedRoleUser>().eq("user_id", userId));
        if (associatedRoleUsers.size() == 0) {
            return new ArrayList<>();
        }
        List<String> roleIds = associatedRoleUsers.stream().map(AssociatedRoleUser::getRoleId).collect(Collectors.toList());
        return roleService.list(new QueryWrapper<Role>().in("id", roleIds));
    }
    public List<String> getPermissions(String userId) {
        return combineAuthCacheService.getPermissions(userId);
    }
}
