package com.BE.starTroad.service;

import com.BE.starTroad.domain.Studier;
import com.BE.starTroad.repository.SpringDataJpaStudierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaStudierService {

    @Autowired
    private SpringDataJpaStudierRepository springDataJpaStudierRepository;


    //스터디 아이디로 몇 명이 스터디에 참여하는지 조회
    public int studyNum(int studyId) {
        List<Studier> studiers = new ArrayList<>();
        springDataJpaStudierRepository.findByStudyId(studyId).forEach(e -> studiers.add(e));
        int num = studiers.size();
        return num;
    }

    public Optional<Studier> findAmIJoin(int studyId, String email) {
        Optional<Studier> studier = springDataJpaStudierRepository.findByStudyIdAndEmail(studyId, email);
        return studier;
    }

    public Studier save(Studier studier) {
        springDataJpaStudierRepository.save(studier);
        return studier;
    }

    public List<Studier> findJoinStudy(String email) {
        List<Studier> joinStudy = new ArrayList<>();
        springDataJpaStudierRepository.findByEmail(email).forEach(e -> joinStudy.add(e));

        return joinStudy;
    }

    public Studier quit(int studyId, String email) {

        Optional<Studier> quitRequester = springDataJpaStudierRepository.findByStudyIdAndEmail(studyId, email);

        if (quitRequester.isPresent()) {
            springDataJpaStudierRepository.delete(quitRequester.get());
        }
        return quitRequester.get();
    }

    public Studier deleteStudier(int id) {

        Optional<Studier> delStudier = springDataJpaStudierRepository.findById(id);

        if (delStudier.isPresent()) {
            springDataJpaStudierRepository.delete(delStudier.get());
            return delStudier.get();
        }
        else {
            return null;
        }
    }

    public List<Studier> findByStudyId(int studyId) {
        List<Studier> studiers = new ArrayList<>();
        springDataJpaStudierRepository.findByStudyId(studyId).forEach(e -> studiers.add(e));
        return studiers;
    }

}
