package com.BE.starTroad.service;

import com.BE.starTroad.domain.Study;
import com.BE.starTroad.repository.SpringDataJpaStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaStudyService {

    @Autowired
    private SpringDataJpaStudyRepository springDataJpaStudyRepository;

    //로드맵별 스터디 조회
    public List<Study> findByFollowMap(int mapId) {
        List<Study> studies = new ArrayList<>();
        springDataJpaStudyRepository.findByFollowMap(mapId).forEach(e -> studies.add(e));
        return studies;
    }
    //개별 스터디 조회
    public Optional<Study> findById(int id) {
        Optional <Study> study = springDataJpaStudyRepository.findById(id);
        return study;
    }

    public Study save(Study study) {
        springDataJpaStudyRepository.save(study);
        return study;
    }

    public List<Study> findByLeader(String email) {
        List<Study> studies = new ArrayList<>();
        springDataJpaStudyRepository.findByLeader(email).forEach(e -> studies.add(e));

        return studies;
    }

    public Study update(int id, Study study) {
        Optional<Study> dbStudy = springDataJpaStudyRepository.findById(id);

        if (dbStudy.isPresent()) {
            dbStudy.get().setName(study.getName());
            dbStudy.get().setFollowMap(study.getFollowMap());
            dbStudy.get().setLeader(study.getLeader());
            dbStudy.get().setCreatedAt(study.getCreatedAt());
            dbStudy.get().setDescription(study.getDescription());
            dbStudy.get().setMaxNum(study.getMaxNum());
            dbStudy.get().setStatus(study.getStatus());
            springDataJpaStudyRepository.save(dbStudy.get());
            return study;
        }
        else {
            return null;
        }
    }

    public Study deleteStudy(int studyId) {
        Optional<Study> delStudy = springDataJpaStudyRepository.findById(studyId);

        if (delStudy.isPresent()) {
            springDataJpaStudyRepository.delete(delStudy.get());
            return delStudy.get();
        }
        else {
            return null;
        }
    }


}
