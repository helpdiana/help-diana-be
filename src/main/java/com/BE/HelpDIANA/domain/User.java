package com.BE.HelpDIANA.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;
import java.util.Collection;


@Entity(name="user")
public class User implements UserDetails {

    @Id
    private String email;

    private String name;

    @Column(columnDefinition = "boolean default false")
    private boolean doctor;

    @Column(nullable = true)
    private String urlImage;

    @Column(nullable = true)
    private String hospital;

    @Column(nullable = true)
    private String profile;

    public User(String tokenOwner, String name, boolean doctor,String urlImage, String hospital, String profile) {
        this.email = tokenOwner;
        this.name = name;
        this.doctor = doctor;
        this.urlImage = urlImage;
        this.hospital = hospital;
        this.profile = profile;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public User(){}

    public User(String email, String name, boolean doctor) {
        this.email = email;
        this.name = name;
        this.doctor = doctor;
    }

    public boolean isDoctor() {
        return doctor;
    }

    public void setDoctor(boolean doctor) {
        this.doctor = doctor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        String path = "/home/ubuntu/develop/" + email;
        File newer = new File(path);
        if (newer.mkdirs()) {
            System.out.println("유저 디렉토리 생성 성공");
        } else {
            System.out.println("유저 디렉토리 생성 실패");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.email;
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
