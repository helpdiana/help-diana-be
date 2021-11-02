package com.BE.starTroad.repository;

import com.BE.starTroad.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SpringDataJpaUserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
}
