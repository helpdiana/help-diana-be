package com.diana.controller;

import com.diana.domain.Diagnose;
import com.diana.form.DiagnoseForm;
import com.diana.repository.DiagnoseRepository;
import com.diana.repository.UserRepository;
import com.diana.security.CurrentUser;
import com.diana.security.UserPrincipal;
import com.diana.service.DiagnoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping({"/api/diagnose"})
public class DiagnoseController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DiagnoseRepository diagnoseRepository;

    public DiagnoseController() {
    }

    //진단서 생성
    @PostMapping({"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@CurrentUser UserPrincipal userPrincipal, @RequestParam(value="image", required=false) List<MultipartFile> files,
                                 @RequestParam(value="name") String name, @RequestParam(value="date") SimpleDateFormat date) throws Exception {
        String email = userPrincipal.getEmail();
        DiagnoseForm form = new DiagnoseForm(email,name,date);

        //return DiagnoseService.create(form, files);
        return new ResponseEntity(DiagnoseService.create(form, files), HttpStatus.OK);
    }

    //진단서 조회 ocr
    @GetMapping("/ocr/{diagnose_id}")
    public ResponseEntity<List<String>> ocr_receive(@CurrentUser UserPrincipal userPrincipal,Long diagnose_id) {
        String email = userPrincipal.getEmail();
        List<String> lists = new ArrayList<>();
        Optional<Diagnose> diagnose = diagnoseRepository.findById(diagnose_id);
        Diagnose ocrDiagnose = diagnose.get();
        if(ocrDiagnose.getEmail().equals(email)){ //본인 진단서만 조회가능
            for (int i=0;i<ocrDiagnose.getImg().size();i++){
                lists.addAll(ocrDiagnose.getImg().get(i).getTranslate_bf());
            }
        }
        return new ResponseEntity(lists, HttpStatus.OK);
    }

    //진단서 조회 변환 후 (bf af 다보내줄거임.)
    @GetMapping("/highlight/{diagnose_id}")
    public ResponseEntity<Map<String, Object>> highlight_receive(@CurrentUser UserPrincipal userPrincipal,Long diagnose_id) {
        String email = userPrincipal.getEmail();
        List<String> listA = new ArrayList<>();
        List<String> listB = new ArrayList<>();
        Optional<Diagnose> diagnose = diagnoseRepository.findById(diagnose_id);
        Diagnose ocrDiagnose = diagnose.get();
        if(ocrDiagnose.getEmail().equals(email)){ //본인 진단서만 조회가능
            for (int i=0;i<ocrDiagnose.getImg().size();i++){
                listA.addAll(ocrDiagnose.getImg().get(i).getTranslate_af());
                listB.addAll(ocrDiagnose.getImg().get(i).getTranslate_bf());
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("translate_af", listA);
        map.put("translate_bf", listB);
        return new ResponseEntity(map, HttpStatus.OK);
    }

/*
@PostMapping({"/add"})
    public ResponseEntity<Diagnose> add(DiagnoseForm diagnose, List<File> image, @CurrentUser UserPrincipal userPrincipal) throws IOException {
        String email = userPrincipal.getEmail();
        Diagnose newDiagnose = new Diagnose();
        newDiagnose.setEmail(email);
        newDiagnose.setName(diagnose.getName());
        newDiagnose.setDate(diagnose.getDate());
        newDiagnose.setFilePath(email);
        if (!image.isEmpty()) {
            List<Image> newImage = new ArrayList();
            newDiagnose.makeDir();

            for(int i = 0; i < image.size(); ++i) {
                File f = (File)image.get(i);
                UUID uid = UUID.randomUUID();
                String savedName = uid.toString() + "_" + f.getName();
                ((Image)newImage.get(i)).setDiagnose(newDiagnose);
                ((Image)newImage.get(i)).setOrigImageName(f.getName());
                ((Image)newImage.get(i)).setImagePath(newDiagnose.getFilePath());
                File target = new File(newDiagnose.getFilePath(), savedName);
                FileCopyUtils.copy((File)image.get(i), target);
            }
        }

        return new ResponseEntity(DiagnoseService.save(newDiagnose), HttpStatus.OK);
    }
*/




    //나의 진단서 조회
    @GetMapping({"/diagnose"})
    public ResponseEntity<List<Diagnose>> receive(@CurrentUser UserPrincipal userPrincipal) {
        String email = userPrincipal.getEmail();
        List<Diagnose> diagnoses = DiagnoseService.myDiagnoses(email);
        return new ResponseEntity(diagnoses, HttpStatus.OK);
    }
}

