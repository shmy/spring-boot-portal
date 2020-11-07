package tech.shmy.portal.application.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.shmy.portal.application.domain.User;
import tech.shmy.portal.application.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserRestController extends RestControllerImpl<User, UserService> {
}
