package com.BE.starTroad.repository;

import com.BE.starTroad.domain.Studier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpringDataJpaStudierRepository extends JpaRepository<Studier, Integer> {

    List<Studier> findByStudyId(int study_id);
    Optional<Studier> findByStudyIdAndEmail(int studyId, String email);
    List<Studier> findByEmail(String email);

}
