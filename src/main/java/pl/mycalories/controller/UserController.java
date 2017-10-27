package pl.mycalories.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping
    public ResponseEntity<Principal> user(Principal user) {
        if(user == null) {
            return new ResponseEntity<Principal>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Principal>(user, HttpStatus.OK);
    }
}
