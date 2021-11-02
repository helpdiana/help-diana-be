package com.BE.starTroad.controller;

import java.util.List;

public class TalkForm {

    private Long id;

    private String name;
    private String created_at;
    private int talk_roadmap;
    private String talk_writer;
    private String description;
    private String talkValid;
    private List<CommentForm> myComments;

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getTalk_roadmap() {
        return talk_roadmap;
    }

    public void setTalk_roadmap(int talk_roadmap) {
        this.talk_roadmap = talk_roadmap;
    }

    public String getTalk_writer() {
        return talk_writer;
    }

    public void setTalk_writer(String talk_writer) {
        this.talk_writer = talk_writer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTalkValid() {
        return talkValid;
    }

    public void setTalkValid(String talkValid) {
        this.talkValid = talkValid;
    }

    public List<CommentForm> getMyComments() {
        return myComments;
    }

    public void setMyComments(List<CommentForm> myComments) {
        this.myComments = myComments;
    }
}
