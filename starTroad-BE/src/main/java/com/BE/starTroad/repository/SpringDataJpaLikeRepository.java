package com.BE.starTroad.repository;

import com.BE.starTroad.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataJpaLikeRepository extends JpaRepository<Like, Long> {

    public Optional<Like> findByEmailAndRoadmapId(String email, int roadmapId);
    List<Like> findByRoadmapId(int roadmapId);
}
