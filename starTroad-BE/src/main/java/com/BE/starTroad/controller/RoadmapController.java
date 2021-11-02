package com.BE.starTroad.controller;

import com.BE.starTroad.config.JwtTokenUtil;
import com.BE.starTroad.domain.*;
import com.BE.starTroad.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/roadmap")
public class RoadmapController {

    @Autowired
    JpaRoadmapService jpaRoadmapService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    JpaLikeService jpaLikeService;
    @Autowired
    JpaTalkService jpaTalkService;
    @Autowired
    JpaCommentService jpaCommentService;
    @Autowired
    JpaStudyService jpaStudyService;
    @Autowired
    JpaStudierService jpaStudierService;
    @Autowired
    JpaRequestService jpaRequestService;

    @PostMapping(value="/generate")
    public ResponseEntity<Roadmap> generate(RoadmapForm roadmap, @RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        String timestamp = roadmap.getCreated_at();
        Timestamp time;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(timestamp);
            time = new Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            time = new Timestamp(System.currentTimeMillis());
        }
        Roadmap newRoadmap = new Roadmap();

    	newRoadmap.setName(roadmap.getName());
        newRoadmap.setCreated_at(time);
        newRoadmap.setTag(roadmap.getTag());
        newRoadmap.setSummary(roadmap.getSummary());
        newRoadmap.setDescription(roadmap.getDescription());
        newRoadmap.setOwner(tokenOwner);
        newRoadmap.setGenerator(tokenOwner);
        newRoadmap.setInformation(roadmap.getInformation());
        newRoadmap.setLikeCount(0);
        newRoadmap.setImage(roadmap.getImage());

        return new ResponseEntity<Roadmap>(jpaRoadmapService.save(newRoadmap), HttpStatus.OK);
    }

    @GetMapping(value="/search")
    public ResponseEntity<List<Roadmap>> search(String name) {

	    List<Roadmap> roadmapsName = new ArrayList<>();

        List<Roadmap> roadmaps = new ArrayList<>();

        roadmapsName = jpaRoadmapService.findByName(name);

        roadmaps.addAll(roadmapsName);

        return new ResponseEntity<List<Roadmap>> (roadmaps, HttpStatus.OK);
    }

    @GetMapping(value="/myroadmap")
    public ResponseEntity<List<Roadmap>> myroadmap(@RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        List<Roadmap> roadmaps = new ArrayList<>();

        roadmaps = jpaRoadmapService.myRoadmaps(tokenOwner);

        return new ResponseEntity<List<Roadmap>>(roadmaps, HttpStatus.OK);
    }

    @GetMapping(value="/{roadmap_id}")
    public ResponseEntity<RoadmapForm> getInfo(@PathVariable("roadmap_id") Long mapId, @RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        Optional<Roadmap> rdmap = jpaRoadmapService.findById(mapId);

        Optional<Like> likeValid = jpaLikeService.findByEmailAndRoadmap_id(tokenOwner, mapId.intValue());
        String likeVal;

        if (likeValid.isPresent()) {
            likeVal = "yes";
        }
        else {
            likeVal = "no";
        }

        RoadmapForm rdform = new RoadmapForm();
        if (rdmap.isPresent()) {
            Roadmap roadmap = rdmap.get();
            rdform.setId(mapId.intValue());
            rdform.setName(rdmap.get().getName());
            rdform.setCreated_at(rdmap.get().getCreated_at().toString());
            rdform.setTag(rdmap.get().getTag());
            rdform.setSummary(rdmap.get().getSummary());
            rdform.setDescription(rdmap.get().getDescription());
            rdform.setOwner(rdmap.get().getOwner());
            rdform.setGenerator(rdmap.get().getGenerator());
            rdform.setInformation(rdmap.get().getInformation());
            rdform.setLike_count(rdmap.get().getLikeCount());
            rdform.setImage(rdmap.get().getImage());

            if (rdmap.get().getOwner().equals(tokenOwner)) {
                rdform.setValid("yes");
            }
            else {
                rdform.setValid("no");
            }
            rdform.setLikeValid(likeVal);

            return new ResponseEntity<RoadmapForm>(rdform,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value="/modify/{roadmap_id}")
    public ResponseEntity<Roadmap> modify(@PathVariable("roadmap_id") Long mapId, RoadmapForm roadmap,
                                          @RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        Optional<Roadmap> rdMap = jpaRoadmapService.findById(mapId);
        String roadmapOwner = "";

        if (rdMap.isPresent()) {
            roadmapOwner = rdMap.get().getOwner();
        }
        if (!(tokenOwner.equals(roadmapOwner))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        String timestamp = roadmap.getCreated_at();
        Timestamp time;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            Date parsedDate = dateFormat.parse(timestamp);
            time = new Timestamp(parsedDate.getTime());
        } catch(Exception e) {
            time = new Timestamp(System.currentTimeMillis());
        }
        Roadmap newRoadmap = new Roadmap();

        newRoadmap.setName(roadmap.getName());
        newRoadmap.setCreated_at(time);
        newRoadmap.setTag(roadmap.getTag());
        newRoadmap.setSummary(roadmap.getSummary());
        newRoadmap.setDescription(roadmap.getDescription());
        newRoadmap.setOwner(roadmapOwner);
        newRoadmap.setGenerator(rdMap.get().getGenerator());
        newRoadmap.setInformation(roadmap.getInformation());
        newRoadmap.setLikeCount(roadmap.getLike_count());
        newRoadmap.setImage(roadmap.getImage());

        Roadmap rdmap = jpaRoadmapService.update(mapId, newRoadmap);

        if (rdmap != null) {
            return new ResponseEntity<Roadmap>(rdmap, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/fork")
    public ResponseEntity<Roadmap> fork(Long id, @RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        Roadmap rdmap = new Roadmap();
        Optional<Roadmap> roadmap = jpaRoadmapService.findById(id);

        if (roadmap.isPresent()) {
            rdmap = jpaRoadmapService.forkRoadmap(tokenOwner, roadmap.get());

            if (rdmap != null) {
                return new ResponseEntity<Roadmap>(rdmap, HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    //로드맵 삭제 : 해당 로드맵에 포함된 코멘트 -> 토크/ 리퀘스트, 스터디원 -> 스터디 삭제
    // -> 로드맵 삭제
    @DeleteMapping(value="/delete/{roadmap_id}")
    public ResponseEntity<Roadmap> deleteRoadmap(@PathVariable("roadmap_id") Long id,
                                                 @RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        Optional<Roadmap> dbRoadmap = jpaRoadmapService.findById(id);
        String roadmapOwner = "";

        if (dbRoadmap.isPresent()) {
            roadmapOwner = dbRoadmap.get().getOwner();
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        if (!(tokenOwner.equals(roadmapOwner))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        //토크 조회 & 해당 토크 아래의 코멘트 삭제 -> 토크 삭제
        int mapId = id.intValue();
        int talkListSize;
        int commentListSize;
        int studyListSize;
        int requestListSize;
        int studierListSize;
        int likeListSize;
        Long talkId;
        Long likeId;
        int studyId;


        List<Talk> talks;
        List<Comment> comments;
        List<Study> studies;
        List<Request> requests;
        List<Studier> studiers;
        List<Like> likes;

        //해당 로드맵에 속하는 토크 조회
        talks = jpaTalkService.findByTalk_Roadmap(mapId);
        talkListSize = talks.size();
        for (int i=0;i<talkListSize;i++) {
            talkId = talks.get(i).getId();
            //해당 토크 속하는 코멘트 조회
            comments = jpaCommentService.findByTalk(talkId);
            commentListSize = comments.size();
            //코멘트 삭제
            for (int j=0;i<commentListSize;i++) {
                jpaCommentService.deleteComment(comments.get(i).getId());
            }
            //토크 삭제
            jpaTalkService.deleteTalk(talkId);
        }

        //해당 로드맵에 속하는 스터디 조회
        studies = jpaStudyService.findByFollowMap(mapId);
        studyListSize = studies.size();
        for (int i=0;i<studyListSize;i++) {
            studyId = studies.get(i).getId();
            //해당 스터디와 연관 있는 리퀘스트와 스터디원 조회
            requests = jpaRequestService.findByStudyId(studyId);
            requestListSize = requests.size();
            studiers = jpaStudierService.findByStudyId(studyId);
            studierListSize = studiers.size();
            //리퀘스트 삭제
            for (int j=0;j<requestListSize;j++) {
                jpaRequestService.deny(requests.get(i).getId());
            }
            //스터디원 삭제
            for (int j=0;j<studierListSize;j++) {
                jpaStudierService.deleteStudier(studiers.get(i).getId());
            }
            //스터디 삭제
            jpaStudyService.deleteStudy(studyId);
        }
        //좋아요 삭제
        likes =jpaLikeService.findByRoadmap_id(mapId);
        likeListSize = likes.size();
        for (int i=0;i<likeListSize;i++) {
            likeId = likes.get(i).getId();
            jpaLikeService.deleteLike(likeId);
        }


        //로드맵 삭제
        Roadmap rdmap = jpaRoadmapService.deleteRoadmap(mapId);

        if (rdmap != null) {
            return new ResponseEntity<Roadmap>(rdmap, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value="/like/{roadmap_id}")
    public ResponseEntity<Roadmap> like(@PathVariable("roadmap_id") Long id, @RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        Optional<Roadmap> roadmap = jpaRoadmapService.findById(id);
        Like like = new Like();
        like.setEmail(tokenOwner);
        like.setRoadmapId(id.intValue());

        if (roadmap.isPresent()) {
            jpaRoadmapService.upCount(roadmap.get());
            jpaLikeService.pushLike(like);
            return new ResponseEntity<Roadmap>(roadmap.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Roadmap>(roadmap.get(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value="/unlike/{roadmap_id}")
    public ResponseEntity<Roadmap> unlike(@PathVariable("roadmap_id") Long id,  @RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        Optional<Roadmap> roadmap = jpaRoadmapService.findById(id);

        Optional<Like> dbLike = jpaLikeService.findByEmailAndRoadmap_id(tokenOwner, id.intValue());
        Long likeId = dbLike.get().getId();

        if (roadmap.isPresent()) {
            jpaRoadmapService.downCount(roadmap.get());
            jpaLikeService.unpushLike(likeId);
            return new ResponseEntity<Roadmap>(roadmap.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Roadmap>(roadmap.get(), HttpStatus.BAD_REQUEST);
        }

    }
}
