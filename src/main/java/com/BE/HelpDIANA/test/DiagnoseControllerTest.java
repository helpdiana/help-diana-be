package com.BE.HelpDIANA.test;

import com.BE.HelpDIANA.config.JwtTokenUtil;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.text.ParseException;
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
                                 String name, String date,  @RequestHeader("Authorization") String token) throws Exception {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

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
            for (Image photo : ImageList) {
                // photo 마다 trans af bf 저장
                TranslateService.imageOfOcr(photo);
                bf.add(photo.getTranslate_bf());
                //diagnose.addAf(photo.getTranslate_af());
            }
            diagnose.setDiagnose_bf(bf.toString());
        }

        //ocr_total.json file 생성 후 저장
        JSONObject obj = new JSONObject();
        obj.put("diagnose_total_bf", diagnose.getDiagnose_bf());

        try {
            FileWriter file = new FileWriter("/Users/kimbokyeong/Desktop/develop/"+diagnose.getEmail()+"/"
            +diagnose.getCreated()+"/ocr_total.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        diagnoseRepository.save(diagnose);
        return new ResponseEntity(diagnose, HttpStatus.OK);
    }

    @GetMapping({"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createToOcr(Long diagnose_id, @RequestHeader("Authorization") String token){

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        Diagnose diagnose = resultDiagnose.get();

        return new ResponseEntity(diagnose.getDiagnose_bf(), HttpStatus.OK);
    }

    //ocr update
    @PostMapping({"/ocr/update"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity ocrUpdate(Long diagnose_id, String name, String date,String diagnose_bf, @RequestHeader("Authorization") String token) throws Exception {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        Diagnose diagnose = resultDiagnose.get();

        diagnose.setName(name);
        diagnose.setDate(Date.valueOf(date));
        diagnose.setDiagnose_bf(diagnose_bf);

        //json file에 update.
        JSONObject obj = new JSONObject();
        obj.put("diagnose_total_bf", diagnose_bf);

        try {
            FileWriter file = new FileWriter("/Users/kimbokyeong/Desktop/develop/"+diagnose.getEmail()+"/"
                    +diagnose.getCreated()+"/ocr_total.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        diagnoseRepository.save(diagnose);
        return new ResponseEntity(diagnose, HttpStatus.OK);
    }
    @GetMapping({"/translate"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity ocrToTrans(Long diagnose_id, @RequestHeader("Authorization") String token){
        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        Diagnose diagnose = resultDiagnose.get();

        //diagnose_bf json 파일을 읽어 translate
        TranslateService.translatePythonExe(diagnose.getFilePath());

        //++생성된 json 파일을 읽어 diagnose af에 string으로 저장.
        JSONParser parse = new JSONParser();


        ////////++++여기서 key 값이랑 value 값 설정해서 다시 넣어야함.
        try {
            FileReader reader = new FileReader("/User/kimbokyeong/Desktop/develop"+diagnose.getEmail()
                    +diagnose.getCreated()+"/trans_ocr_total.json");
            Object obj = parse.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            reader.close();

            System.out.print(jsonObject);
            diagnose.setDiagnose_af(jsonObject.toString());

        } catch (IOException | org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }

        diagnoseRepository.save(diagnose);
        return new ResponseEntity(diagnose, HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity transUpdate(Long diagnose_id, String diagnose_af, @RequestHeader("Authorization") String token){
        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        Optional<Diagnose> resultDiagnose = diagnoseRepository.findById(diagnose_id);
        Diagnose diagnose = resultDiagnose.get();

        //++생성된 json 파일을 읽어 diagnose af에 string으로 update.
        //++json file에 update.
        JSONObject obj = new JSONObject();
        obj.put("diagnose_total_af", diagnose_af);

        try {
            FileWriter file = new FileWriter("/Users/kimbokyeong/Desktop/develop/"+diagnose.getEmail()+"/"
                    +diagnose.getCreated()+"/trans_ocr_total.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        diagnose.setDiagnose_af(diagnose_af);

        diagnoseRepository.save(diagnose);
        return new ResponseEntity(diagnose, HttpStatus.OK);
    }
}

