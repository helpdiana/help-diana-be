package com.BE.starTroad.service;

import com.BE.starTroad.domain.Like;
import com.BE.starTroad.repository.SpringDataJpaLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaLikeService {

    @Autowired
    private SpringDataJpaLikeRepository springDataJpaLikeRepository;

    public void pushLike(Like like) {
        springDataJpaLikeRepository.save(like);
    }

    public void unpushLike(Long id) {

        springDataJpaLikeRepository.deleteById(id);
    }

    public Optional<Like> findByEmailAndRoadmap_id(String email, int mapId) {
        Optional<Like> like = springDataJpaLikeRepository.findByEmailAndRoadmapId(email, mapId);

        return like;
    }

    public List<Like> findByRoadmap_id(int mapId) {
        List<Like> likes = new ArrayList<>();
        springDataJpaLikeRepository.findByRoadmapId(mapId).forEach(e -> likes.add(e));

        return likes;
    }

    public Like deleteLike(Long id) {
        Optional<Like> deleteLike = springDataJpaLikeRepository.findById(id);

        if (deleteLike.isPresent()) {
            springDataJpaLikeRepository.delete(deleteLike.get());
            return deleteLike.get();
        }
        else {
            return null;
        }
    }
}
