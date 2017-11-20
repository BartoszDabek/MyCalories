package pl.mycalories.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import pl.mycalories.model.User;

public interface UserService extends AbstractService<User, Long>, UserDetailsService {
    User checkUserAuthentication(String name, String password);
}
