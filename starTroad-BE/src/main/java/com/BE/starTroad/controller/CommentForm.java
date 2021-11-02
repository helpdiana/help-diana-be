package com.BE.starTroad.controller;

public class CommentForm {

    private Long id;

    private String created_at;
    private int comment_talk;
    private String comment_writer;
    private String content;
    private String commentValid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getComment_talk() {
        return comment_talk;
    }

    public void setComment_talk(int comment_talk) {
        this.comment_talk = comment_talk;
    }

    public String getComment_writer() {
        return comment_writer;
    }

    public void setComment_writer(String comment_writer) {
        this.comment_writer = comment_writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommentValid() {
        return commentValid;
    }

    public void setCommentValid(String commentValid) {
        this.commentValid = commentValid;
    }
}
