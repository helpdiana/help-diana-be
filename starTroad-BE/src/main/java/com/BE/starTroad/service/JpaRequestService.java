package com.BE.starTroad.service;

import com.BE.starTroad.domain.Request;
import com.BE.starTroad.repository.SpringDataJpaRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaRequestService {

    @Autowired
    private SpringDataJpaRequestRepository springDataJpaRequestRepository;

    public Optional<Request> findByStudyIdAndRequester(int studyId, String email) {

        Optional<Request> request = springDataJpaRequestRepository.findByStudyIdAndRequester(studyId, email);

        return request;
    }

    public List<Request> findByRequester(String email) {
        List<Request> requests = new ArrayList<>();
        springDataJpaRequestRepository.findByRequester(email).forEach(e -> requests.add(e));

        return requests;
    }

    public Request save(Request request) {
        springDataJpaRequestRepository.save(request);

        return request;
    }

    public List<Request> findByHead(String email) {
        List<Request> requests = new ArrayList<>();
        springDataJpaRequestRepository.findByHead(email).forEach(e -> requests.add(e));

        return requests;
    }

    public List<Request> findByStudyId(int id) {
        List<Request> requests = new ArrayList<>();
        springDataJpaRequestRepository.findByStudyId(id).forEach(e -> requests.add(e));

        return requests;
    }
    public Request accept(int id) {
        Optional<Request> acceptRequest = springDataJpaRequestRepository.findById(id);

        if (acceptRequest.isPresent()) {
            springDataJpaRequestRepository.delete(acceptRequest.get());
            return acceptRequest.get();
        }
        else {
            return null;
        }
    }
    public Request deny(int id) {
        Optional<Request> denyRequest = springDataJpaRequestRepository.findById(id);

        if (denyRequest.isPresent()) {
            springDataJpaRequestRepository.delete(denyRequest.get());
            return denyRequest.get();
        }
        else {
            return null;
        }
    }
}
