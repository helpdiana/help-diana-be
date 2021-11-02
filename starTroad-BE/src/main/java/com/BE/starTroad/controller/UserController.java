package com.BE.starTroad.controller;

import com.BE.starTroad.config.JwtTokenUtil;
import com.BE.starTroad.domain.User;
import com.BE.starTroad.service.JpaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    JpaUserService jpaUserService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @PostMapping(value="/login/add")
    public ResponseEntity<User> addInfo(User user, @RequestHeader("Authorization") String token){

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        String email = user.getEmail();
        System.out.println(tokenOwner);
        System.out.println(email);

        if (!(tokenOwner.equals(email))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        User updateUser = jpaUserService.update(email, user);

        if (updateUser != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value="/")
    public ResponseEntity<User> getInfo(@RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        Optional<User> findUser = jpaUserService.findByEmail(tokenOwner);

        if (findUser.isPresent()) {
            return new ResponseEntity<User>(findUser.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(value="/{user_email}")
    public ResponseEntity<User> saveInfo(@PathVariable("user_email")String email, User user,
                                         @RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        Optional<User> findUser = jpaUserService.findByEmail(email);

        if (!(tokenOwner.equals(email))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        User updateUser = jpaUserService.update(email, user);

        if (updateUser != null) {
            return new ResponseEntity<User>(updateUser, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

}
