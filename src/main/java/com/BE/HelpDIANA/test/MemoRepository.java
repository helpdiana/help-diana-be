package com.BE.HelpDIANA.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {
    public Memo findById(int id);

    public List<Memo> findByEmail(String email);

    public List<Memo> findByEmailAndDate(String email, Date date);

}