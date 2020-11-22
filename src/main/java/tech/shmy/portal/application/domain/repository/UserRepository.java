package tech.shmy.portal.application.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.shmy.portal.application.domain.entity.User;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsernameAndPassword(String username, String password);
}
