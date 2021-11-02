package com.diana.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getTranslate_bf() {
        return translate_bf;
    }

    public void setTranslate_bf(List<String> translate_bf) {
        this.translate_bf = translate_bf;
    }

    public List<String> getTranslate_af() {
        return translate_af;
    }

    public void setTranslate_af(List<String> translate_af) {
        this.translate_af = translate_af;
    }

    public Image(Long diagnoseId, String origImageName, Long imageSize, String imagePath) {
        this.diagnoseId = diagnoseId;
        this.origImageName = origImageName;
        this.imageSize = imageSize;
        this.imagePath = imagePath;
    }

    public Long getDiagnoseId() {
        return diagnoseId;
    }

    public void setDiagnoseId(Long diagnoseId) {
        this.diagnoseId = diagnoseId;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigImageName() {
        return this.origImageName;
    }

    public void setOrigImageName(String origImageName) {
        this.origImageName = origImageName;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getImageSize() {
        return this.imageSize;
    }

    public void setImageSize(Long imageSize) {
        this.imageSize = imageSize;
    }
}
