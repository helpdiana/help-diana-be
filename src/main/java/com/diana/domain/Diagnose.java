package com.diana.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity(name="diagnose")
public class Diagnose {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = User.class)@JoinColumn(name = "user_id", updatable = false)
    //@JsonBackReference private User user;

    @Email
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private final String created = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"))+System.nanoTime() ;

    @Column(nullable = true)
    private String filePath;

    @Column(nullable = false)
    private SimpleDateFormat date;

    @Column(nullable = true)
    private String bf;

    public String getBf() {
        return bf;
    }

    public void setBf(String bf) {
        this.bf = bf;
    }

    public String getAf() {
        return af;
    }

    public void setAf(String af) {
        this.af = af;
    }

    @Column(nullable = true)
    private String af;



    @Transient
    private List<Image> img = new ArrayList();

    public Diagnose(String email, String name, SimpleDateFormat date) {
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
        this.filePath = "/Users/kimbokyeong/Desktop/develop/" + this.email + "/" + this.created + "/";
                //Paths.get("C:", "Desktop", "develop", email, this.created).toString();
    }

    public void updateDiagnose(String name, SimpleDateFormat date) {
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

    public SimpleDateFormat getDate() {
        return this.date;
    }

    public void setDate(SimpleDateFormat date) {
        this.date = date;
    }

    public List<Image> getImg() {
        return this.img;
    }

    public void setImg(List<Image> img) {
        this.img = img;
    }
}
