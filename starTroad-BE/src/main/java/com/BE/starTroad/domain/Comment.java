package com.BE.starTroad.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name="comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp created_at;

    @Column(name = "comment_talk")
    private int commentTalk;

    @Column(name = "comment_writer")
    private String commentWriter;

    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getCommentTalk() {
        return commentTalk;
    }

    public void setCommentTalk(int commentTalk) {
        this.commentTalk = commentTalk;
    }

    public String getCommentWriter() {
        return commentWriter;
    }

    public void setCommentWriter(String commentWriter) {
        this.commentWriter = commentWriter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
