package com.BE.starTroad.repository;

import com.BE.starTroad.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataJpaRequestRepository extends JpaRepository<Request, Integer> {

    Optional<Request> findByStudyIdAndRequester(int studyId, String email);
    List<Request> findByRequester(String email);
    List<Request> findByHead(String email);
    List<Request> findByStudyId(int studyId);
}
