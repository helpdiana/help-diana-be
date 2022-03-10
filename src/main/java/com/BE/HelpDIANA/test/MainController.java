package com.BE.HelpDIANA.test;

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
@RequestMapping("/api/main")
public class MainController {

    @Autowired
    JpaUserService jpaUserService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;


    @GetMapping(value="")
    public ResponseEntity<User> main() {
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
}


