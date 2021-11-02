package com.BE.starTroad.controller;

import com.BE.starTroad.config.JwtTokenUtil;
import com.BE.starTroad.domain.Request;
import com.BE.starTroad.domain.Studier;
import com.BE.starTroad.domain.Study;
import com.BE.starTroad.service.JpaRequestService;
import com.BE.starTroad.service.JpaStudierService;
import com.BE.starTroad.service.JpaStudyService;
import com.BE.starTroad.service.JpaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin

@RestController
@RequestMapping("/api/study")
public class RequestController {

    @Autowired
    JpaStudyService jpaStudyService;

    @Autowired
    JpaStudierService jpaStudierService;

    @Autowired
    JpaRequestService jpaRequestService;

    @Autowired
    JpaUserService jpaUserService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    //나의 스터디
    @GetMapping(value="/mystudy")
    public ResponseEntity<List<StudyForm>> myStudy(@RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        List<StudyForm> myStudies = new ArrayList<>();
        List<Studier> myStudiers = jpaStudierService.findJoinStudy(tokenOwner);
        Study study = new Study();
        int listSize = myStudiers.size();
        int studyId;
        for (int i=0;i<listSize;i++) {
            studyId = myStudiers.get(i).getStudyId();
            study = jpaStudyService.findById(studyId).get();
            if (study != null) {

                StudyForm studyForm = new StudyForm();
                studyForm.setId(studyId);
                studyForm.setName(study.getName());
                studyForm.setFollow_map(study.getFollowMap());
                studyForm.setLeader(study.getLeader());
                studyForm.setCreated_at(study.getCreatedAt().toString());
                studyForm.setDescription(study.getDescription());
                studyForm.setMax_num(study.getMaxNum());
                studyForm.setStatus(study.getStatus());

                if (study.getLeader().equals(tokenOwner)) {
                    studyForm.setValid("yes");
                    studyForm.setJoinValid("yes");
                }
                else {
                    studyForm.setValid("no");
                    studyForm.setJoinValid("yes");
                }
                //now num
                int num = jpaStudierService.studyNum(studyId);
                studyForm.setNow_num(num);
                myStudies.add(studyForm);
            }
        }
        return new ResponseEntity<List<StudyForm>>(myStudies, HttpStatus.OK);
    }

    //스터디 참여 요청 리스트
    @GetMapping(value = "/requestlist")
    public ResponseEntity<List<RequestForm>> requestList(@RequestHeader ("Authorization") String token) {
        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        List<Request> myRequests = jpaRequestService.findByHead(tokenOwner);
        String name = "";
        int study_id;
        int roadmap_id;
        String study_name = "";
        String requester = "";
        List<RequestForm> reqForm = new ArrayList<>();
        int listSize = myRequests.size();
        for (int i=0;i<listSize;i++) {
            RequestForm req = new RequestForm();
            req.setId(myRequests.get(i).getId());
            study_id = myRequests.get(i).getStudyId();
            req.setStudy_id(study_id);
            study_name = jpaStudyService.findById(study_id).get().getName();
            req.setStudy_name(study_name);
            roadmap_id = jpaStudyService.findById(study_id).get().getFollowMap();
            req.setRoadmap_id(roadmap_id);
            req.setHead(myRequests.get(i).getHead());
            requester = myRequests.get(i).getRequester();
            req.setRequester(requester);
            name = jpaUserService.findByEmail(requester).get().getName();
            req.setRequesterName(name);
            reqForm.add(req);
        }
        return new ResponseEntity<List<RequestForm>>(reqForm, HttpStatus.OK);
    }

}
