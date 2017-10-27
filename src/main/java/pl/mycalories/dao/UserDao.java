package pl.mycalories.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mycalories.model.User;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
