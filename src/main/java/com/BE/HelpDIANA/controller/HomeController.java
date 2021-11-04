package com.BE.HelpDIANA.controller;

import com.BE.HelpDIANA.repository.UserRepository;
import com.BE.HelpDIANA.config.JwtTokenUtil;
import com.BE.HelpDIANA.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/")
    public ResponseEntity home(@RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        Optional<User> user = userRepository.findByEmail(tokenOwner);
        if (user.isPresent()) {


                return new ResponseEntity<>(null, HttpStatus.OK);
            }
        else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


    }

}
