package com.BE.starTroad.repository;

import com.BE.starTroad.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataJpaCommentRepository extends JpaRepository<Comment, Long> {

    public List<Comment> findAllByCommentTalk(int talkId);
}
