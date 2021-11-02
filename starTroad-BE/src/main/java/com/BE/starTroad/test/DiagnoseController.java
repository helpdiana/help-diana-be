package com.BE.starTroad.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping({"/api/diagnose"})
public class DiagnoseController {

    @Autowired
    private DiagnoseRepository diagnoseRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ImageHandler imageHandler;

    //진단서 생성
    @PostMapping({"/add"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@RequestPart(value="files", required=false) List<MultipartFile> files,
                                 String name, String date) throws Exception {
        String email = "email";
        DiagnoseForm form = new DiagnoseForm(email,name, Date.valueOf(date));

        // 파일 처리를 위한 Diagnose 객체 생성
        Diagnose diagnose = new Diagnose(form.getEmail(), form.getName(), form.getDate());
        diagnose.setFilePath(form.getEmail());
        diagnoseRepository.save(diagnose);

        if(files.isEmpty()){
            diagnose.setImg(null);
            return new ResponseEntity(diagnose, HttpStatus.OK);
        }
        else if(files==null){
            diagnose.setImg(null);
            return new ResponseEntity(diagnose, HttpStatus.OK);
        }
        // 파일이 존재할 때에만 처리
        else {
            List<Image> ImageList = imageHandler.parseFileInfo(diagnose, files);
            for (Image photo : ImageList) {
                // 파일을 폴더에 저장
                diagnose.addImage(imageRepository.save(photo));
            }
            System.out.println("파일 저장 완료");
            TranslateService.translatePythonExe(diagnose.getFilePath()); // python 실행
            for (Image photo : ImageList) {
                // photo 마다 trans af bf 저장
                TranslateService.photoOfTrans(photo);
            }
        }
        return new ResponseEntity(diagnose, HttpStatus.OK);


    }

    @PostMapping("/api/addbase")
    public ResponseEntity addData(@RequestBody List<FileDTO> fileDTOList, String name, String date) {
        String email = "email";
        DiagnoseForm form = new DiagnoseForm(email,name, Date.valueOf(date));

        // 파일 처리를 위한 Diagnose 객체 생성
        Diagnose diagnose = new Diagnose(form.getEmail(), form.getName(), form.getDate());
        diagnose.setFilePath(form.getEmail());
        diagnoseRepository.save(diagnose);
        String fileBase64;

        if(fileDTOList.isEmpty()){
            diagnose.setImg(null);
            return new ResponseEntity(diagnose, HttpStatus.OK);
        }
        else if(fileDTOList==null){
            diagnose.setImg(null);
            return new ResponseEntity(diagnose, HttpStatus.OK);
        }
        // base64form list
        for(int i = 0 ; i <fileDTOList.size() ; i++){
            fileBase64 = fileDTOList.get(i).getFileBase64();

            // 파일이 업로드되지 않았거나 사이즈가 큰 경우를 체크합니다.
            // 사이즈는 일반 바이트에서 1.33을 곱하면 BASE64 사이즈가 대략 나옵니다.
            if(fileBase64 == null || fileBase64.equals("")) {
                return new ResponseEntity("FileIsNull", HttpStatus.BAD_REQUEST);
            }
            else if(fileBase64.length() > 400000) {
                return new ResponseEntity("FileIsTooBig", HttpStatus.BAD_REQUEST);
            }

            try {
                String fileName = System.nanoTime() + fileDTOList.get(i).getFileName(); // 파일네임은 서버에서 결정하거나 JSON에서 받아옵니다.
                // 저장할 파일 경로를 지정합니다.
                File file = new File(diagnose.getFilePath() + fileName);
                // BASE64를 일반 파일로 변환하고 저장합니다.
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] decodedBytes = decoder.decode(fileBase64.getBytes());
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(decodedBytes);
                fileOutputStream.close();

                Image image = new Image(diagnose.getId(), fileName,null , diagnose.getFilePath());
                //imageRepository.save(image);

                // 생성 후 리스트에 추가
                diagnose.addImage(imageRepository.save(image));


            } catch(IOException e) {
                System.err.println(e);
                return new ResponseEntity("FileIsNotUploaded", HttpStatus.BAD_REQUEST);
            }
        }
        /*TranslateService.translatePythonExe(diagnose.getFilePath()); // python 실행
        for (Image photo : diagnose.getImg()) {
            // photo 마다 trans af bf 저장
            TranslateService.photoOfTrans(photo);
        }*/

        return new ResponseEntity(diagnose, HttpStatus.OK);
    }







    //진단서 조회 ocr
    @GetMapping("/ocr/{diagnose_id}")
    public ResponseEntity<List<String>> ocr_receive(Long diagnose_id) {
        String email = "qhrud15@gmail.com";
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
    public ResponseEntity<Map<String, Object>> highlight_receive(Long diagnose_id) {
        String email = "qhrud15@gmail.com";
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

    //나의 진단서 조회
    @GetMapping({"/diagnose"})
    public ResponseEntity<List<Diagnose>> receive() {
        String email = "qhrud15@gmail.com";
        List<Diagnose> diagnoses = DiagnoseService.myDiagnoses(email);
        return new ResponseEntity(diagnoses, HttpStatus.OK);
    }
}

