package com.BE.starTroad.repository;

import com.BE.starTroad.domain.Talk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataJpaTalkRepository extends JpaRepository<Talk, Long> {

    public List<Talk> findByTalkRoadmap(int mapId);
}
