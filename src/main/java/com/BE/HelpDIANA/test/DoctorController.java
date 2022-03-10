package com.BE.HelpDIANA.test;


import com.BE.HelpDIANA.config.JwtTokenUtil;
import com.BE.HelpDIANA.domain.User;
import com.BE.HelpDIANA.service.JpaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping({"/api/doctor"})
public class DoctorController {
    @Autowired
    private DiagnoseRepository diagnoseRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    DiagnoseService diagnoseService;
    @Autowired
    private ImageHandler imageHandler;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    JpaUserService jpaUserService;

    @Autowired
    private NoticeRepository noticeRepository;

    //요청한 진단서 리스트 출력
    @GetMapping(value="")
    public ResponseEntity<List> requestList(){

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<User> findUser = jpaUserService.findByEmail(tokenOwner);
        User user = findUser.get();

        if(user.isDoctor()==false)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<Diagnose> resultDiagnose = diagnoseRepository.findByRequestTrue();

        if(resultDiagnose.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<List>(resultDiagnose, HttpStatus.OK);
    }

    //의사의 진단서들
    @GetMapping(value="/my")
    public ResponseEntity<List> myList(){

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<User> findUser = jpaUserService.findByEmail(tokenOwner);
        User user = findUser.get();

        if(user.isDoctor()==false)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        List<Diagnose> resultDiagnose = diagnoseRepository.findByDoctor(tokenOwner);

        if(resultDiagnose.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<List>(resultDiagnose, HttpStatus.OK);
    }

    //의사가 진단서 선택
    @PostMapping(value = "/check")
    public ResponseEntity checkDiagnose(Long diagnose_id){
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<User> findUser = jpaUserService.findByEmail(tokenOwner);
        User user = findUser.get();

        if(user.isDoctor()==false)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        Optional<Diagnose> checkDiagnose = diagnoseRepository.findById(diagnose_id);

        if(checkDiagnose.isEmpty()){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

        Diagnose diagnose = checkDiagnose.get();

        if(diagnose.getDoctor()!=null)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        Notice notice = new Notice();
        notice.setDiagnose_id(diagnose_id); //////////
        notice.setNoticeEmail(diagnose.getEmail());
        notice.setNoticeDoctor(tokenOwner);
        notice.setCheck(true);
        notice.setOpinion(false);

        diagnose.setDoctor(tokenOwner);
        diagnose.setRequest(false);
        diagnoseRepository.save(diagnose);
        noticeRepository.save(notice);


        return new ResponseEntity(diagnose, HttpStatus.OK);
    }

    //의사가 의견 추가
    @PostMapping(value = "/add")
    public ResponseEntity addDiagnose(Long diagnose_id, String opinion){
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<User> findUser = jpaUserService.findByEmail(tokenOwner);
        User user = findUser.get();

        if(user.isDoctor()==false)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        Optional<Diagnose> checkDiagnose = diagnoseRepository.findById(diagnose_id);

        if(checkDiagnose.isEmpty()){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

        Diagnose diagnose = checkDiagnose.get();

        if(diagnose.getDoctor()==null)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        if(!diagnose.getDoctor().equals(tokenOwner)){
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }

        try {
            FileWriter file = new FileWriter(diagnose.getFilePath()+"/doctor.json");
            file.write(String.valueOf(opinion));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Notice notice = new Notice();
        notice.setDiagnose_id(diagnose_id);
        notice.setNoticeEmail(diagnose.getEmail());
        notice.setNoticeDoctor(tokenOwner);
        notice.setCheck(true);
        notice.setOpinion(true);

        diagnoseRepository.save(diagnose);
        noticeRepository.save(notice);

        return new ResponseEntity(diagnose, HttpStatus.OK);
    }

    //의사가 의견 수정
    @PutMapping(value = "/update")
    public ResponseEntity updateDiagnose(Long diagnose_id, String opinion){
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<User> findUser = jpaUserService.findByEmail(tokenOwner);
        User user = findUser.get();

        if(user.isDoctor()==false)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        Optional<Diagnose> checkDiagnose = diagnoseRepository.findById(diagnose_id);

        if(checkDiagnose.isEmpty()){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }

        Diagnose diagnose = checkDiagnose.get();

        if(diagnose.getDoctor()==null)
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);

        if(diagnose.getDoctor()!=tokenOwner){
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }

        try {
            FileWriter file = new FileWriter(diagnose.getFilePath()+"/doctor.json");
            file.write(String.valueOf(opinion));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Notice notice = new Notice();
        notice.setNoticeEmail(diagnose.getEmail());
        notice.setNoticeDoctor(tokenOwner);
        notice.setCheck(true);
        notice.setOpinion(true);

        diagnoseRepository.save(diagnose);
        noticeRepository.save(notice);

        return new ResponseEntity(diagnose, HttpStatus.OK);
    }
}
