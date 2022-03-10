package com.BE.HelpDIANA.test;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.BE.HelpDIANA.config.JwtTokenUtil;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping({"/api/reserve"})
public class ReserveController {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ExamineRepository examineRepository;

    @Autowired
    private DiagnoseRepository diagnoseRepository;

    @Autowired
    private MemoRepository memoRepository;



    @PostMapping(value = "/add/clinic")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createClinic(String name, String date, String start, String end) {
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Clinic clinic = new Clinic();

        clinic.setEmail(tokenOwner);
        clinic.setName(name);
        clinic.setDate(Date.valueOf(date));
        clinic.setStartTime(Time.valueOf(start));
        clinic.setEndTime(Time.valueOf(end));
        System.out.println(name + date + start);

        clinicRepository.save(clinic);

        return new ResponseEntity(clinic, HttpStatus.OK);
    }

    @PostMapping(value = "/add/examine")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createExamine(String name, String date, String start, String end) {
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";
        System.out.println("examine 생성");

        Examine examine = new Examine();

        examine.setEmail(tokenOwner);
        examine.setName(name);
        examine.setDate(Date.valueOf(date));
        examine.setStartTime(Time.valueOf(start));
        examine.setEndTime(Time.valueOf(end));

        examineRepository.save(examine);

        return new ResponseEntity(examine, HttpStatus.OK);
    }

    @GetMapping(value = "/clinic")
    public ResponseEntity<List<Clinic>> receiveClinic(Long id) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Clinic> clinicOne = clinicRepository.findById(id);

        Clinic clinic = clinicOne.get();

        if (clinic == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (!clinic.getEmail().equals(tokenOwner)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(clinic, HttpStatus.OK);
    }

    @GetMapping(value = "/examine")
    public ResponseEntity<List<Examine>> receiveExamine(Long id) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Examine> examineOne = examineRepository.findById(id);

        Examine examine = examineOne.get();

        if (examine == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (!examine.getEmail().equals(tokenOwner)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(examine, HttpStatus.OK);
    }

    @PutMapping(value = "/update/clinic")
    public ResponseEntity updateClinic(Long id, String name, String date, String start, String end) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Clinic> oneClinic = clinicRepository.findById(id);

        if (!oneClinic.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Clinic clinic = oneClinic.get();

        if (!(clinic.getEmail().equals(tokenOwner))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        clinic.setName(name);
        clinic.setDate(Date.valueOf(date));
        clinic.setStartTime(Time.valueOf(start));
        clinic.setEndTime(Time.valueOf(end));

        clinicRepository.save(clinic);

        return new ResponseEntity<Clinic>(clinic, HttpStatus.OK);
    }

    @PutMapping(value = "/update/examine")
    public ResponseEntity updateExamine(Long id, String name, String date, String start, String end) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Examine> oneExamine = examineRepository.findById(id);

        if (!oneExamine.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Examine examine = oneExamine.get();

        if (!(examine.getEmail().equals(tokenOwner))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        examine.setName(name);
        examine.setDate(Date.valueOf(date));
        examine.setStartTime(Time.valueOf(start));
        examine.setEndTime(Time.valueOf(end));

        examineRepository.save(examine);

        return new ResponseEntity<Examine>(examine, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/clinic")
    public ResponseEntity<Clinic> deleteClinic(Long id) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Clinic> clinicOne = clinicRepository.findById(id);

        Clinic clinic = clinicOne.get();

        if (clinic == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (!clinic.getEmail().equals(tokenOwner)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        clinicRepository.delete(clinic);

        return new ResponseEntity<Clinic>(clinic, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/examine")
    public ResponseEntity<Examine> deleteExamine(Long id) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Examine> examineOne = examineRepository.findById(id);

        Examine examine = examineOne.get();

        if (examine == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (!examine.getEmail().equals(tokenOwner)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        examineRepository.delete(examine);

        return new ResponseEntity<Examine>(examine, HttpStatus.OK);
    }
    ///////////////////////
    @PostMapping(value = "/add/memo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createMemo(String memo, String date) {
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Memo> memoOne = Optional.ofNullable(memoRepository.findByEmailAndDate(tokenOwner, Date.valueOf(date)));

        if (memoOne.isPresent()) {
            Memo newMemo = memoOne.get();

            newMemo.setMemo(memo);
            newMemo.setDate(Date.valueOf(date));

            memoRepository.save(newMemo);

            return new ResponseEntity<Memo>(newMemo, HttpStatus.OK);
        }

        Memo newMemo = new Memo();

        newMemo.setEmail(tokenOwner);
        newMemo.setMemo(memo);
        newMemo.setDate(Date.valueOf(date));

        memoRepository.save(newMemo);

        return new ResponseEntity(newMemo, HttpStatus.OK);
    }
    @GetMapping(value = "/memo")
    public ResponseEntity<List<Clinic>> receiveMemo(Long id) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Memo> memoOne = memoRepository.findById(id);

        Memo newMemo = memoOne.get();

        if (newMemo == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (!newMemo.getEmail().equals(tokenOwner)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity(newMemo, HttpStatus.OK);
    }
    @PutMapping(value = "/update/memo")
    public ResponseEntity updateMemo(String memo, String date) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Memo> memoOne = Optional.ofNullable(memoRepository.findByEmailAndDate(tokenOwner, Date.valueOf(date)));

        if (!memoOne.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Memo newMemo = memoOne.get();

        if (!(newMemo.getEmail().equals(tokenOwner))) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        newMemo.setMemo(memo);
        newMemo.setDate(Date.valueOf(date));

        memoRepository.save(newMemo);

        return new ResponseEntity<Memo>(newMemo, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/memo")
    public ResponseEntity<Memo> deleteMemo(Long id) {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Memo> memoOne = memoRepository.findById(id);

        Memo newMemo = memoOne.get();

        if (newMemo == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if (!newMemo.getEmail().equals(tokenOwner)) {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        memoRepository.delete(newMemo);

        return new ResponseEntity<Memo>(newMemo, HttpStatus.OK);
    }
}
