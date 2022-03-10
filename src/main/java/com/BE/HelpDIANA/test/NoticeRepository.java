package com.BE.HelpDIANA.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long>{
    Notice findByNoticeId(Long noticeId);

    List<Notice> findByNoticeEmail(String noticeEmail);

    List<Notice> findByNoticeDoctor(String noticeDoctor);
    List<Notice> findByNoticeEmailAndNoticeCheckTrue(String noticeEmail);

}
