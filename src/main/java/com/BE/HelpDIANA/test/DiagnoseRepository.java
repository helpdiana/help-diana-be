package com.BE.HelpDIANA.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface DiagnoseRepository extends JpaRepository<Diagnose, Long> {
    Diagnose findById(int id);

    List<Diagnose> findByEmail(String email);

    List<Diagnose> findOrderByDate(String email);

    List<Diagnose> findByEmailAndDate(String email, Date date);

}

