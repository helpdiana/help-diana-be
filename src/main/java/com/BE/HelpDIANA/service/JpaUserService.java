package com.BE.HelpDIANA.service;

import com.BE.HelpDIANA.repository.UserRepository;
import com.BE.HelpDIANA.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserService {

    @Autowired
    private UserRepository userRepository;

    //회원가입
    public User save(User user) {
        userRepository.save(user);
        return user;
    }

    //이메일로 사람 찾기
    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }
    //업데이트 (수정)
    public User update(String email, User user) {
        Optional<User> dbUser = userRepository.findByEmail(email);

        if (dbUser.isPresent()) {
            //System.out.println("이건 db update 전: "+ dbUser.get().isDoctor());
            userRepository.save(user);
            //System.out.println("user 의 정보 : "+user.isDoctor()+"db user update "+dbUser.get().isDoctor());
            return dbUser.get();
        }
        else {
            return null;
        }
    }

}
