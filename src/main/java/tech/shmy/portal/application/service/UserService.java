package tech.shmy.portal.application.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.User;
import tech.shmy.portal.application.domain.mapper.UserMapper;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public User login(String username, String password) {
        QueryWrapper<User> lqw = new QueryWrapper<User>()
                .eq("username", username)
                .eq("password", password);
        User user = getOne(lqw);
        return user;
    }
}
