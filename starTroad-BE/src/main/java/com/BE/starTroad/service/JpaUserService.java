package com.BE.starTroad.service;

import com.BE.starTroad.domain.User;
import com.BE.starTroad.repository.SpringDataJpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserService {

    @Autowired
    private SpringDataJpaUserRepository springDataJpaUserRepository;

    //회원가입
    public User save(User user) {
        springDataJpaUserRepository.save(user);
        return user;
    }

    //이메일로 사람 찾기
    public Optional<User> findByEmail(String email) {
        Optional<User> user = springDataJpaUserRepository.findByEmail(email);
        return user;
    }
    //업데이트 (수정)
    public User update(String email, User user) {
        Optional<User> dbUser = springDataJpaUserRepository.findByEmail(email);

        if (dbUser.isPresent()) {
            dbUser.get().setBirth(user.getBirth());
            dbUser.get().setMessage(user.getMessage());
            dbUser.get().setMajor(user.getMajor());
            dbUser.get().setInterest(user.getInterest());
            springDataJpaUserRepository.save(dbUser.get());
            return dbUser.get();
        }
        else {
            return null;
        }
    }

}
