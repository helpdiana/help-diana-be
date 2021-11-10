package com.BE.HelpDIANA.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping({"/add/clinic"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createClinic(String name, String date, String start, String end, String memo) {
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Clinic clinic = new Clinic();

        clinic.setEmail(tokenOwner);
        clinic.setName(name);
        clinic.setDate(Date.valueOf(date));
        clinic.setStartTime(Time.valueOf(start));
        clinic.setEndTime(Time.valueOf(end));
        clinic.setMemo(memo);
        System.out.println(name + date + start);

        clinicRepository.save(clinic);

        return new ResponseEntity(clinic, HttpStatus.OK);
    }

    @PostMapping({"/add/examine"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createExamine(String name, String date, String start, String end, String memo) {
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Clinic clinic = new Clinic();

        clinic.setEmail(tokenOwner);
        clinic.setName(name);
        clinic.setDate(Date.valueOf(date));
        clinic.setStartTime(Time.valueOf(start));
        clinic.setEndTime(Time.valueOf(end));
        clinic.setMemo(memo);
        System.out.println(name + date + start);

        clinicRepository.save(clinic);

        return new ResponseEntity(clinic, HttpStatus.OK);
    }

    @GetMapping({"{/clinic_id}"})
    public ResponseEntity<List<Clinic>> receiveClinic(@PathVariable("clinic_id") Long id) {

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

    @PutMapping({"update/{clinic_id}"})
    public ResponseEntity updateClinic(@PathVariable("clinic_id") Long id, String name, String date, String start, String end, String memo) {

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
        clinic.setMemo(memo);

        clinicRepository.save(clinic);

        return new ResponseEntity<Clinic>(clinic, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{clinic_id}")
    public ResponseEntity<Clinic> deleteRoadmap(@PathVariable("clinic_id") Long id) {

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
}
