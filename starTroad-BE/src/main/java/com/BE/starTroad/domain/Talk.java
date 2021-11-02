package com.BE.starTroad.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name="talk")
public class Talk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Timestamp created_at;

    @Column(name = "talk_roadmap")
    private int talkRoadmap;

    @Column(name = "talk_writer")
    private String talkWriter;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getTalkRoadmap() {
        return talkRoadmap;
    }

    public void setTalkRoadmap(int talkRoadmap) {
        this.talkRoadmap = talkRoadmap;
    }

    public String getTalkWriter() {
        return talkWriter;
    }

    public void setTalkWriter(String talkWriter) {
        this.talkWriter = talkWriter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
