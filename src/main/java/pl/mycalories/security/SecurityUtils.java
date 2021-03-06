package pl.mycalories.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.mycalories.model.User;
import pl.mycalories.service.UserService;

public class SecurityUtils {

    public static User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        if (principal instanceof User) {
            return (User) principal;
        }
        return null;
    }

    public static User getCurrentUser(UserService userService) {
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            return userService.findById(currentUser.getId());
        }
        return null;
    }
}
