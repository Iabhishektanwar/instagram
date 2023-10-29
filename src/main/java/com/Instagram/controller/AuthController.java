package com.Instagram.controller;

import com.Instagram.Exceptions.UserException;
import com.Instagram.modal.User;
import com.Instagram.repository.UserRepository;
import com.Instagram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<User> registerUserHandler(@RequestBody User user) throws UserException {
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<User>(registeredUser, HttpStatus.OK);
    }

    @GetMapping("/signin")
    public ResponseEntity<User> signInHandler(Authentication authentication) throws UserException {
        Optional<User> opt = userRepository.findByEmail(authentication.getName());
        if(opt.isPresent()) {
            return new ResponseEntity<User>(opt.get(), HttpStatus.ACCEPTED);
        }
        throw new BadCredentialsException("Invalid username or password");
    }
}
