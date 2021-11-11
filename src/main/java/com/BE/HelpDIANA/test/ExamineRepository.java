package com.BE.HelpDIANA.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ExamineRepository extends JpaRepository<Examine, Long> {
    public Examine findById(int id);

    public List<Examine> findByEmail(String email);

    public List<Examine> findOrderByDate(String email);

    public List<Examine> findOrderByName(String email);

    public List<Examine> findByEmailAndDate(String email, Date date);

}