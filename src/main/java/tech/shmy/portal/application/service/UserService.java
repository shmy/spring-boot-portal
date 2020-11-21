package tech.shmy.portal.application.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.User;
import tech.shmy.portal.application.domain.mapper.UserMapper;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}
