package com.BE.HelpDIANA.controller;

import com.BE.HelpDIANA.config.JwtTokenUtil;
import com.BE.HelpDIANA.domain.User;
import com.BE.HelpDIANA.service.JpaUserService;
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

    @GetMapping(value="")
    public ResponseEntity<User> getInfo() {
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";
        Optional<User> findUser = jpaUserService.findByEmail(tokenOwner);

        if (findUser.isPresent()) {
            return new ResponseEntity<User>(findUser.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping(value="/update")
    public ResponseEntity<User> updateInfo(String name, boolean doctor, String urlImage, String hospital, String profile) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";
        User user;

        if(doctor==true)
            user = new User(tokenOwner, name, doctor ,urlImage, hospital,profile);

        else
            user = new User(tokenOwner, name, doctor);

        System.out.println(doctor);

        User updateUser = jpaUserService.update(tokenOwner, user);

        if (updateUser != null) {
            return new ResponseEntity(updateUser, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }

}
