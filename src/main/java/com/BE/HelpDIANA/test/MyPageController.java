package com.BE.HelpDIANA.test;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping({"/api/myPage"})
public class MyPageController {
    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ExamineRepository examineRepository;

    @Autowired
    private DiagnoseRepository diagnoseRepository;

    @Autowired
    private MemoRepository memoRepository;

    @GetMapping(value = "")
    public ResponseEntity myPage(String date){
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        List<Clinic> clinics = clinicRepository.findByEmailAndDate(tokenOwner,java.sql.Date.valueOf(date));
        List<Examine> examines = examineRepository.findByEmailAndDate(tokenOwner,java.sql.Date.valueOf(date));
        List<Diagnose> diagnoses = diagnoseRepository.findByEmailAndDate(tokenOwner, java.sql.Date.valueOf(date));
        List<Memo> memos = memoRepository.findByEmailAndDate(tokenOwner, java.sql.Date.valueOf(date));

        JSONObject data = new JSONObject();
        data.put("diagnoses", diagnoses);
        data.put("clinics", clinics);
        data.put("examines", examines);
        data.put("memos", memos);

        return new ResponseEntity(data, HttpStatus.OK);

    }
}
