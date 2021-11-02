package com.BE.starTroad.controller;

public class StudyForm {

    private int id;

    private String name;
    private int follow_map;
    private String leader;
    private String created_at;
    private String description;
    private int max_num;
    private int now_num;
    private int status;
    private String valid;
    private String joinValid;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollow_map() {
        return follow_map;
    }

    public void setFollow_map(int follow_map) {
        this.follow_map = follow_map;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMax_num() {
        return max_num;
    }

    public void setMax_num(int max_num) {
        this.max_num = max_num;
    }

    public int getNow_num() {
        return now_num;
    }

    public void setNow_num(int now_num) {
        this.now_num = now_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getJoinValid() {
        return joinValid;
    }

    public void setJoinValid(String joinValid) {
        this.joinValid = joinValid;
    }
}
