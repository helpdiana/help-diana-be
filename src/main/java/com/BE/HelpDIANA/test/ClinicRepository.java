package com.BE.HelpDIANA.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    public Clinic findById(int id);

    public List<Clinic> findByEmail(String email);

    public List<Clinic> findOrderByDate(String email);

    public List<Clinic> findOrderByName(String email);

    public List<Clinic> findByEmailAndDate(String email,Date date);

}