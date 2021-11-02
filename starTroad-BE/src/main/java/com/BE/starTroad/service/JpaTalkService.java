package com.BE.starTroad.service;

import com.BE.starTroad.domain.Talk;
import com.BE.starTroad.repository.SpringDataJpaTalkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaTalkService {

    @Autowired
    private SpringDataJpaTalkRepository springDataJpaTalkRepository;

    public List<Talk> findAll() {
        List<Talk> talks = new ArrayList<>();
        springDataJpaTalkRepository.findAll().forEach(e -> talks.add(e));
        return talks;
    }

    public List<Talk> findByTalk_Roadmap(int mapId) {
        List<Talk> talks = new ArrayList<>();
        springDataJpaTalkRepository.findByTalkRoadmap(mapId).forEach(e -> talks.add(e));
        return talks;
    }

    public Talk save(Talk talk) {
        springDataJpaTalkRepository.save(talk);
        return talk;
    }

    public Optional<Talk> findById(Long talkId) {
        Optional<Talk> talk = springDataJpaTalkRepository.findById(talkId);
        return talk;
    }
    public Talk update(Long id, Talk talk) {
        Optional<Talk> dbTalk = springDataJpaTalkRepository.findById(id);

        if (dbTalk.isPresent()) {
            dbTalk.get().setId(id);
            dbTalk.get().setName(talk.getName());
            //dbTalk.get().setCreated_at(talk.getCreated_at());
            dbTalk.get().setTalkRoadmap(talk.getTalkRoadmap());
            dbTalk.get().setTalkWriter(talk.getTalkWriter());
            dbTalk.get().setDescription(talk.getDescription());
            springDataJpaTalkRepository.save(dbTalk.get());
            return dbTalk.get();
        }
        else {
            return null;
        }
    }

    public Talk deleteTalk(Long talkId) {
        Optional<Talk> delTalk = springDataJpaTalkRepository.findById(talkId);

        if (delTalk.isPresent()) {
            springDataJpaTalkRepository.delete(delTalk.get());
            return delTalk.get();
        }
        else {
            return null;
        }
    }
}
