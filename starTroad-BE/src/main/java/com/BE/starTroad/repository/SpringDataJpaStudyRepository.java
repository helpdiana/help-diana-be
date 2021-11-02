package com.BE.starTroad.repository;

import com.BE.starTroad.domain.Study;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataJpaStudyRepository extends JpaRepository<Study, Integer> {

    List<Study> findByName(String name);
    List<Study> findByFollowMap(int mapId);
    List<Study> findByLeader(String email);

}
