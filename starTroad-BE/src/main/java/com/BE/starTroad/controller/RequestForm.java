package com.BE.starTroad.controller;

public class RequestForm {

    private int id;
    private String head;
    private String requester;
    private String requesterName;
    private int study_id;
    private String study_name;
    private int roadmap_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public int getStudy_id() {
        return study_id;
    }

    public void setStudy_id(int study_id) {
        this.study_id = study_id;
    }

    public String getStudy_name() {
        return study_name;
    }

    public void setStudy_name(String study_name) {
        this.study_name = study_name;
    }

    public int getRoadmap_id() {
        return roadmap_id;
    }

    public void setRoadmap_id(int roadmap_id) {
        this.roadmap_id = roadmap_id;
    }
}
