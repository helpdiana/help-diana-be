package com.BE.HelpDIANA.controller;

import com.BE.HelpDIANA.repository.UserRepository;
import com.BE.HelpDIANA.config.JwtTokenUtil;
import com.BE.HelpDIANA.domain.User;
import com.BE.HelpDIANA.test.Diagnose;
import com.BE.HelpDIANA.test.DiagnoseRepository;
import com.BE.HelpDIANA.test.Notice;
import com.BE.HelpDIANA.test.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private DiagnoseRepository diagnoseRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping(value = "/notice")
    public ResponseEntity<List> home() {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<User> user = userRepository.findByEmail(tokenOwner);
        if (user.isPresent()) {
            User user1 = user.get();

            //user가 의사일때 요청된 리스트 보여줌
            if(user1.isDoctor()){
                List<Diagnose> resultDiagnose = diagnoseRepository.findByRequestTrue();

                if(resultDiagnose.isEmpty())
                    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

                return new ResponseEntity<List>(resultDiagnose, HttpStatus.OK);
            }

            List<Notice> notices = noticeRepository.findByNoticeEmailAndNoticeCheckTrue(tokenOwner);

            if(notices.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            return new ResponseEntity<List>(notices, HttpStatus.OK);
            }
        else {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/notice/check")
    public ResponseEntity checkNotice(Long notice_id) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<User> user = userRepository.findByEmail(tokenOwner);
        if (user.isPresent()) {
            User user1 = user.get();

            Notice resultNotice = noticeRepository.findByNoticeId(notice_id);

            if(resultNotice==null)
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            noticeRepository.delete(resultNotice);

            return new ResponseEntity(resultNotice, HttpStatus.OK);
        }
        else {
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping(value = "/doctors")
    public ResponseEntity<List> allDoctor() {
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<User> user = userRepository.findByEmail(tokenOwner);
        if (user.isPresent()) {
            List<User> doctors = userRepository.findByDoctorTrue();

            if(doctors.isEmpty())
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            return new ResponseEntity<List>(doctors, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


}
