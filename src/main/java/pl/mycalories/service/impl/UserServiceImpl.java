package pl.mycalories.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.mycalories.dao.UserDao;
import pl.mycalories.model.User;
import pl.mycalories.service.UserService;

@Service
public class UserServiceImpl extends AbstractServiceImpl<User, Long, UserDao>
        implements UserService {

    @Autowired
    public UserServiceImpl(UserDao repository) {
        super(repository);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findByUsername(username);
        user.setPassword("{noop}" + user.getPassword());
        return user;
    }

    @Override
    public User checkUserAuthentication(String name, String password) {
        User user = repository.findByUsername(name);
        if (user == null || !password.equals(user.getPassword())) {
            throw new AuthenticationCredentialsNotFoundException("Incorrect username or password");
        }
        return user;
    }
}
