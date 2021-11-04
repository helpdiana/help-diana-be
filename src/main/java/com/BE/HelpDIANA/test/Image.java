package com.BE.HelpDIANA.test;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Long diagnoseId;

    @Column(nullable = false)
    private String origImageName;

    @Column(nullable = false)
    private Long imageSize;

    @Column(nullable = false)
    private String imagePath;

    @Transient
    private List<String> translate_bf = new ArrayList();

    @Transient
    private List<String> translate_af = new ArrayList();

    public Image(Long diagnoseId, String origImageName, Long imageSize, String imagePath) {
        this.diagnoseId = diagnoseId;
        this.origImageName = origImageName;
        this.imageSize = imageSize;
        this.imagePath = imagePath;
    }
    public Image(){

    }
}
