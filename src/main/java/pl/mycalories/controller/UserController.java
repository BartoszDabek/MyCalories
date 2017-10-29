package pl.mycalories.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mycalories.model.User;
import pl.mycalories.security.SecurityUtils;
import pl.mycalories.service.RoleService;
import pl.mycalories.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<User> user(Principal user) {
        if(user == null) {
            return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<User>(SecurityUtils.getCurrentUser(userService), HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<?> create(@RequestBody User user) {

        user.getRoles().add(roleService.findById(2L));
        User createdUser = userService.save(user);

        return new ResponseEntity<User>(createdUser, HttpStatus.CREATED);
    }
}
