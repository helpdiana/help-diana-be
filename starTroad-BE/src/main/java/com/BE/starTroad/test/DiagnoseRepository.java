package com.BE.starTroad.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {
    Diagnose findById(int id);

    List<Diagnose> findByEmail(String email);

    List<Diagnose> findOrderByDate(String email);

    List<Diagnose> findOrderByName(String email);

}

