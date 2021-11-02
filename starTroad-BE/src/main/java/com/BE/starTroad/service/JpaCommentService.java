package com.BE.starTroad.service;

import com.BE.starTroad.domain.Comment;
import com.BE.starTroad.repository.SpringDataJpaCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaCommentService {

    @Autowired
    private SpringDataJpaCommentRepository springDataJpaCommentRepository;

    public Comment save(Comment comment){
	    springDataJpaCommentRepository.save(comment);
	    return comment;
    }

    //talk 아이디로 comment 조회
    public List<Comment> findByTalk(Long talkId){
        int id = talkId.intValue();
        List<Comment> comments = new ArrayList<>();
        springDataJpaCommentRepository.findAllByCommentTalk(id).forEach(e -> comments.add(e));
        return comments;
    }

    public Comment update(Long id, Comment comment) {
        Optional<Comment> dbComment = springDataJpaCommentRepository.findById(id);

        if (dbComment.isPresent()) {
            dbComment.get().setId(id);
            //dbComment.get().setCreated_at(comment.getCreated_at());
            dbComment.get().setCommentTalk(comment.getCommentTalk());
            dbComment.get().setCommentWriter(comment.getCommentWriter());
            dbComment.get().setContent(comment.getContent());
            springDataJpaCommentRepository.save(dbComment.get());
            return dbComment.get();
        }
        else {
            return null;
        }
    }

    public Optional<Comment> findById(Long commentId) {
        Optional<Comment> comment = springDataJpaCommentRepository.findById(commentId);
        return comment;
    }

    public Comment deleteComment(Long commentId) {
        Optional<Comment> delComment = springDataJpaCommentRepository.findById(commentId);

        if (delComment.isPresent()) {
            springDataJpaCommentRepository.delete(delComment.get());
            return delComment.get();
        }
        else {
            return null;
        }

    }
}
