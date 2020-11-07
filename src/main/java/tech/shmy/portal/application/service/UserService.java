package tech.shmy.portal.application.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.User;

@Service
public class UserService extends ServiceImpl<User.UserMapper, User> {
}
