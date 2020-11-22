package tech.shmy.portal.application.domain.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.shmy.portal.application.domain.entity.Token;
import tech.shmy.portal.application.domain.mapper.TokenMapper;

@Service
public class TokenService extends ServiceImpl<TokenMapper, Token> {

}
