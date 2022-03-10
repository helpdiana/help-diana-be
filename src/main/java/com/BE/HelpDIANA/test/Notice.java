package com.BE.HelpDIANA.test;

import javax.persistence.*;

@Entity(name="notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column
    private String noticeEmail;

    @Column
    private String noticeDoctor;

    @Column
    private boolean noticeCheck;

    @Column
    private boolean noticeOpinion;

    @Column
    private Long diagnose_id;

    public Long getDiagnose_id() {
        return diagnose_id;
    }

    public void setDiagnose_id(Long diagnose_id) {
        this.diagnose_id = diagnose_id;
    }

    public Long getId() {
        return noticeId;
    }

    public void setId(Long id) {
        this.noticeId = id;
    }

    public String getNoticeEmail() {
        return noticeEmail;
    }

    public void setNoticeEmail(String noticeEmail) {
        this.noticeEmail = noticeEmail;
    }

    public String getNoticeDoctor() {
        return noticeDoctor;
    }

    public void setNoticeDoctor(String noticeDoctor) {
        this.noticeDoctor = noticeDoctor;
    }

    public boolean isCheck() {
        return noticeCheck;
    }

    public void setCheck(boolean check) {
        this.noticeCheck = check;
    }

    public boolean isOpinion() {
        return noticeOpinion;
    }

    public void setOpinion(boolean opinion) {
        this.noticeOpinion = opinion;
    }
    public Notice(){

    }
}
