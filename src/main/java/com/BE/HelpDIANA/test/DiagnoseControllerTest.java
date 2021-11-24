package com.BE.HelpDIANA.test;

import com.BE.HelpDIANA.config.JwtTokenUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping({"/api/diagnoseTest"})
public class DiagnoseControllerTest {

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

    //진단서 생성 (사진, 이름, 날짜 입력 --> ocr 처리만.)
    @PostMapping({"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestPart(value="files", required=false) List<MultipartFile> files,
                                 String name, String date) throws Exception {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";
        // 파일 처리를 위한 Diagnose 객체 생성
        Diagnose diagnose = new Diagnose(tokenOwner, name, Date.valueOf(date));
        diagnose.setFilePath(tokenOwner);
        diagnoseRepository.save(diagnose);

        // 파일 없는 진단서 생성 했을경우
        if(files==null||files.isEmpty()){
            diagnose.setImg(null);
            return new ResponseEntity(diagnose, HttpStatus.OK);
        }
        // 파일이 존재할 때에만 처리
        else {
            System.out.println("파일 존재함");
            List<Image> ImageList = imageHandler.parseFileInfo(diagnose, files); //image를 local에 저장하는 함수 호출
            List<List> bf = new ArrayList<>();
            for (Image photo : ImageList) {
                // 파일을 DB에 저장
                diagnose.addImage(imageRepository.save(photo));
            }
            System.out.println("파일 폴더, DB 저장 완료");
            TranslateService.ocrPythonExe(diagnose.getFilePath()); // ocr 하는 함수 실행.
        }
        TranslateService.ocrToEngPythonExe(diagnose.getFilePath());
        TranslateService.EngToKorPythonExe(diagnose.getFilePath());
        return new ResponseEntity(diagnose, HttpStatus.OK);
    }

    @GetMapping(value = "/ocr")
    public ResponseEntity createToOcr(Long diagnose_id){

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        if(resultDiagnose.isEmpty()){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        Diagnose diagnose = resultDiagnose.get();

        if(diagnose.getEmail().equals(tokenOwner)){

            JSONParser parser = new JSONParser();
            JSONObject jsonObject1 = null;
            try {

                Object obj1 = parser.parse(new FileReader(diagnose.getFilePath()+"/ocr_total.json"));
                jsonObject1 = (JSONObject) obj1;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return new ResponseEntity(jsonObject1, HttpStatus.OK);
        }
        else{
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }
    }

    //ocr update
    @PutMapping(value = "/ocr/update")
    public ResponseEntity ocrUpdate(Long diagnose_id, String name, String date,String newJson) throws Exception {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        if(resultDiagnose.isEmpty()){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        Diagnose diagnose = resultDiagnose.get();

        if(!diagnose.getEmail().equals(tokenOwner)){
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }
        diagnose.setName(name);
        diagnose.setDate(Date.valueOf(date));

        try {

            FileWriter file = new FileWriter(diagnose.getFilePath()+"/ocr_total.json");
            file.write(String.valueOf(newJson));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TranslateService.ocrToEngPythonExe(diagnose.getFilePath());
        TranslateService.EngToKorPythonExe(diagnose.getFilePath());
        diagnoseRepository.save(diagnose);
        return new ResponseEntity(diagnose, HttpStatus.OK);
    }
    @GetMapping(value = "/translate")
    public ResponseEntity ocrToTrans(Long diagnose_id){
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        String tokenOwner = "email";

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);

        System.out.println("tras");
        if(resultDiagnose.isEmpty())
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);

        Diagnose diagnose = resultDiagnose.get();

        if(!diagnose.getEmail().equals(tokenOwner)){
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject1 = null;
        try {

            Object obj1 = parser.parse(new FileReader(diagnose.getFilePath()+"/trans_ocr_total.json"));
            jsonObject1 = (JSONObject) obj1;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity(jsonObject1, HttpStatus.OK);

    }

    @PutMapping(value = "/translate/update")
    public ResponseEntity transUpdate(Long diagnose_id, String newJson){
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        if(resultDiagnose.isEmpty())
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);

        Diagnose diagnose = resultDiagnose.get();

        if(!diagnose.getEmail().equals(tokenOwner)){
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }
        try {

            FileWriter file = new FileWriter(diagnose.getFilePath()+"/trans_ocr_total.json");
            file.write(String.valueOf(newJson));
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //diagnose_bf json 파일을 읽어 translate
        //TranslateService.translatePythonExe(diagnose.getFilePath());

        return new ResponseEntity(diagnose, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity deleteDiagnose(Long diagnose_id){
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";


        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        if(resultDiagnose.isEmpty())
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);

        Diagnose diagnose = resultDiagnose.get();

        if(!diagnose.getEmail().equals(tokenOwner)){
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }

        diagnoseRepository.delete(diagnose);

        //folder delete
        System.out.println(diagnose.getFilePath()+" 삭제");
        File deleteFolder = new File(diagnose.getFilePath());

        if(deleteFolder.exists()){
            File[] deleteFolderList = deleteFolder.listFiles();

            for (int j = 0; j < deleteFolderList.length; j++) {
                deleteFolderList[j].delete();
                System.out.println("bobo");
            }

            if(deleteFolder.isDirectory()){
                System.out.println("폴더 삭제");
                deleteFolder.delete();
            }
        }

        return new ResponseEntity(HttpStatus.OK);
    }
    //진단서 조회 변환 후 (bf af 다보내줄거임.)
    @GetMapping("/highlight")
    public ResponseEntity highlight_receive(Long diagnose_id) {
        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        if(resultDiagnose.isEmpty())
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);

        Diagnose diagnose = resultDiagnose.get();

        if(!diagnose.getEmail().equals(tokenOwner)){
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }

        JSONParser parser = new JSONParser();
        JSONObject jsonObject_ocr = null;
        JSONObject jsonObject_trans = null;
        JSONArray total = new JSONArray();
        try {

            Object obj1 = parser.parse(new FileReader(diagnose.getFilePath()+"/ocr_total.json"));
            jsonObject_ocr = (JSONObject) obj1;

            Object obj2 = parser.parse(new FileReader(diagnose.getFilePath()+"/trans_ocr_total.json"));
            jsonObject_trans = (JSONObject) obj2;

            total.add(jsonObject_ocr);
            total.add(jsonObject_trans);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity(total ,HttpStatus.OK);
    }

    @PostMapping(value = "/request")
    public ResponseEntity diagnose_request(Long diagnose_id){

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        if(resultDiagnose.isEmpty())
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);

        Diagnose diagnose = resultDiagnose.get();

        if(!diagnose.getEmail().equals(tokenOwner)){
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }

        diagnose.setRequest(true);

        diagnoseRepository.save(diagnose);
        return new ResponseEntity(diagnose, HttpStatus.OK);
    }




    //Test thread
    //진단서 생성 (사진, 이름, 날짜 입력 --> ocr 처리만.)
    @PostMapping({"/test/ocr"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity testOcr(@RequestPart(value="files", required=false) List<MultipartFile> files,
                                 String name, String date) throws Exception {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";
        // 파일 처리를 위한 Diagnose 객체 생성
        Diagnose diagnose = new Diagnose(tokenOwner, name, Date.valueOf(date));
        diagnose.setFilePath(tokenOwner);
        diagnoseRepository.save(diagnose);

        // 파일 없는 진단서 생성 했을경우
        if(files==null||files.isEmpty()){
            diagnose.setImg(null);
            System.out.println("파일 없음.");
            return new ResponseEntity(diagnose, HttpStatus.OK);
        }
        // 파일이 존재할 때에만 처리
        else {
            System.out.println("파일 존재함");
            List<Image> ImageList = imageHandler.parseFileInfo(diagnose, files); //image를 local에 저장하는 함수 호출
            List<List> bf = new ArrayList<>();
            for (Image photo : ImageList) {
                // 파일을 DB에 저장
                diagnose.addImage(imageRepository.save(photo));
            }
            System.out.println("파일 폴더, DB 저장 완료");
            TranslateService.ocrPythonExe(diagnose.getFilePath()); // ocr 하는 함수 실행.
            TranslateService.ocrToEngPythonExe(diagnose.getFilePath());
        }
        TranslateService.EngToKorPythonExe(diagnose.getFilePath());


        return new ResponseEntity(diagnose, HttpStatus.OK);
    }



    @GetMapping(value = "/test/trans")
    public ResponseEntity ocrTest(Long diagnose_id) throws Exception {

        //token = token.substring(7);
        //String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);
        String tokenOwner = "email";

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        if(resultDiagnose.isEmpty()){
            return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
        }
        Diagnose diagnose = resultDiagnose.get();

        if(!diagnose.getEmail().equals(tokenOwner)){
            return new ResponseEntity(null, HttpStatus.UNAUTHORIZED);
        }

        //diagnose_bf json 파일을 읽어 translate
        TranslateService.translatePythonExe(diagnose.getFilePath());
        diagnoseRepository.save(diagnose);
        return new ResponseEntity(diagnose, HttpStatus.OK);
    }

}

