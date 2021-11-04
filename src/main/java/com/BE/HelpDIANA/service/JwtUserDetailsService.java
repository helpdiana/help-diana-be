package com.BE.HelpDIANA.service;

import java.util.ArrayList;
import java.util.Optional;

import com.BE.HelpDIANA.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private JpaUserService jpaUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        Optional<User> user = jpaUserService.findByEmail(email);

        if (user.isPresent()) {

            String encodeName = passwordEncoder.encode(user.get().getName());
            if (user.get().getEmail().equals(email)) {
                return new org.springframework.security.core.userdetails.User(email, encodeName,
                        new ArrayList<>());
                //return new User(user.get().getName(), email, new ArrayList<>());
            }
            else {
                throw new UsernameNotFoundException("User not found with email: " + email);
            }
        }
        else {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
    }
}