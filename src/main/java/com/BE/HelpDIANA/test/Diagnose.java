package com.BE.HelpDIANA.test;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity(name="diagnose")
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private final String created = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))+System.nanoTime() ;

    @Column(nullable = true)
    private String filePath;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private java.sql.Date date;

    @Transient
    private List<List> bf = new ArrayList<>();

    @Transient
    private List<List> af = new ArrayList<>();

    public List<List> getBf() {
        return bf;
    }

    public void setBf(List<List> bf) {
        this.bf = bf;
    }

    public List<List> getAf() {
        return af;
    }

    public void setAf(List<List> af) {
        this.af = af;
    }

    public void addBf(List bf) {
        this.bf.add(bf);
    }
    public void addAf(List af) {
        this.af.add(af);
    }

    @Transient
    private List<Image> img = new ArrayList();

    public Diagnose(String email, String name, Date date) {
        this.email = email;
        this.name = name;
        this.date = date;
        makeDir();
    }


    public void makeDir() {
        String path = "/Users/kimbokyeong/Desktop/develop/" + this.email + "/" + this.created;
        File newer = new File(path);
        if (newer.mkdirs()) {
            System.out.println("디렉토리 생성 성공");
        } else {
            System.out.println("디렉토리 생성 실패");
        }
    }

    public void setFilePath(String email) {
        this.filePath = "/Users/kimbokyeong/Desktop/develop/" + this.email + "/" + this.created;
                //Paths.get("C:", "Desktop", "develop", email, this.created).toString();
    }

    public void updateDiagnose(String name, Date date) {
        this.name = name;
        this.date = date;
    }

    // Diagnose에서 파일 처리 위함
    public void addImage(Image image) {
        this.img.add(image);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated() {
        return this.created;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Image> getImg() {
        return this.img;
    }

    public void setImg(List<Image> img) {
        this.img = img;
    }
}
