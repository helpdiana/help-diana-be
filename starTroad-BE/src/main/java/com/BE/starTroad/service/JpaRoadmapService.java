package com.BE.starTroad.service;

import com.BE.starTroad.domain.Roadmap;
import com.BE.starTroad.repository.SpringDataJpaRoadmapRepository;
import com.BE.starTroad.repository.SpringDataJpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaRoadmapService {

    @Autowired
    private SpringDataJpaRoadmapRepository springDataJpaRoadmapRepository;
    @Autowired
    private SpringDataJpaUserRepository springDataJpaUserRepository;

    //로드맵 생성
    public Roadmap save(Roadmap roadmap){
        springDataJpaRoadmapRepository.save(roadmap);
        return roadmap;
    }
    //로드맵 업데이트 (수정)
    public Roadmap update(Long id, Roadmap roadmap) {
        Optional<Roadmap> dbRoadmap = springDataJpaRoadmapRepository.findById(id);

        //int intId = id.intValue();

        if (dbRoadmap.isPresent()) {
            dbRoadmap.get().setId(id);
            dbRoadmap.get().setName(roadmap.getName());
            //dbRoadmap.get().setCreated_at(roadmap.getCreated_at());
            dbRoadmap.get().setTag(roadmap.getTag());
            dbRoadmap.get().setDescription(roadmap.getDescription());
            dbRoadmap.get().setOwner(roadmap.getOwner());
            dbRoadmap.get().setGenerator(roadmap.getGenerator());
            dbRoadmap.get().setInformation(roadmap.getInformation());
            dbRoadmap.get().setLikeCount(roadmap.getLikeCount());
            springDataJpaRoadmapRepository.save(dbRoadmap.get());
            return dbRoadmap.get();
        }
        else {
            return null;
        }
    }
    public Optional<Roadmap> findById(Long id) {
        Optional<Roadmap> roadmap = springDataJpaRoadmapRepository.findById(id);
        return roadmap;

    }
    //로드맵 전체 조회
    public List<Roadmap> findAll() {
        List<Roadmap> roadmaps = new ArrayList<>();
        springDataJpaRoadmapRepository.findAll().forEach(e -> roadmaps.add(e));
        return roadmaps;
    }
    //로드맵 이름으로 조회
    public List<Roadmap> findByName(String name) {
        List<Roadmap> roadmaps = new ArrayList<>();
        springDataJpaRoadmapRepository.findByNameLike("%"+name+"%").forEach(e -> roadmaps.add(e));
        return roadmaps;
    }
    //로드맵 태그로 조회
    public List<Roadmap> findByTag(String tag) {
        List<Roadmap> roadmaps = new ArrayList<>();
        springDataJpaRoadmapRepository.findByTagLike("%"+tag+"%").forEach(e -> roadmaps.add(e));
        return roadmaps;
    }
    //로드맵 포크하기
    public Roadmap forkRoadmap(String email, Roadmap roadmap) {

        Long mapID = (long) roadmap.getId();
        Timestamp time = new Timestamp(System.currentTimeMillis());
        if (springDataJpaUserRepository.findByEmail(email).isPresent()) { //fork 하려는 사람이 존재하는지

            if (springDataJpaRoadmapRepository.findById(mapID).isPresent()) { //받은 roadmap이 존재하는지
                Roadmap newRoadmap = new Roadmap();
                newRoadmap.setName(roadmap.getName());
                newRoadmap.setCreated_at(time);
                newRoadmap.setTag(roadmap.getTag());
                newRoadmap.setSummary(roadmap.getSummary());
                newRoadmap.setDescription(roadmap.getDescription());
                newRoadmap.setOwner(email); //fork 하는 사람
                newRoadmap.setGenerator(roadmap.getGenerator());
                newRoadmap.setInformation(roadmap.getInformation());
                newRoadmap.setInformation(roadmap.getInformation());
                newRoadmap.setImage(roadmap.getImage());

                try {
                    springDataJpaRoadmapRepository.save(newRoadmap);
                    return newRoadmap;
                } catch (Exception e) {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        else {
            //not registered user(by email)
            return null;
        }
    }

    //좋아요 수 올리기
    public int upCount(Roadmap roadmap) {

        roadmap.setLikeCount(roadmap.getLikeCount()+1);
        springDataJpaRoadmapRepository.save(roadmap);
        return roadmap.getLikeCount();
    }

    //좋아요 수 내리기
    public int downCount(Roadmap roadmap) {

        roadmap.setLikeCount(roadmap.getLikeCount()-1);
        springDataJpaRoadmapRepository.save(roadmap);
        return roadmap.getLikeCount();
    }

    //나의 로드맵 조회
    public List<Roadmap> myRoadmaps(String email) {

        List<Roadmap> roadmaps = springDataJpaRoadmapRepository.findByOwner(email);

        return roadmaps;
    }

    public Roadmap deleteRoadmap(int mapId) {
        Long roadmapId = (long) mapId;
        Optional<Roadmap> delRoadmap = springDataJpaRoadmapRepository.findById(roadmapId);

        if (delRoadmap.isPresent()) {
            springDataJpaRoadmapRepository.delete(delRoadmap.get());
            return delRoadmap.get();
        }
        else {
            return null;
        }
    }

}
