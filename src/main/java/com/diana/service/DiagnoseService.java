package com.diana.service;


import com.diana.domain.Diagnose;
import com.diana.domain.Image;
import com.diana.form.DiagnoseForm;
import com.diana.repository.DiagnoseRepository;
import com.diana.repository.ImageRepository;
import com.diana.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiagnoseService {
    @Autowired
    private static DiagnoseRepository diagnoseRepository;
    @Autowired
    private static UserRepository userRepository;
    @Autowired
    private static ImageRepository imageRepository;
    @Autowired
    private static ImageHandler imageHandler;



public static Diagnose create(DiagnoseForm diagnoseForm, List<MultipartFile> files) throws Exception {
        // 파일 처리를 위한 Board 객체 생성
        Diagnose diagnose = new Diagnose(diagnoseForm.getEmail(), diagnoseForm.getName(), diagnoseForm.getDate());
        diagnose.setFilePath(diagnoseForm.getEmail());

        List<Image> ImageList = imageHandler.parseFileInfo(diagnose, files);

        // 파일이 존재할 때에만 처리
        if(!ImageList.isEmpty()) {
            for (Image photo : ImageList) {
                // 파일을 폴더에 저장
                diagnose.addImage(imageRepository.save(photo));
            }
            TranslateService.translatePythonExe(diagnose.getFilePath()); // python 실행
            for (Image photo : ImageList) {
                // photo 마다 trans af bf 저장
                TranslateService.photoOfTrans(photo);
            }
        }
        diagnoseRepository.save(diagnose);
        return diagnose;
    }



    /*// 진단서 수정
    public Diagnose update(Long diagnoseId, DiagnoseForm diagnoseForm) throws Exception {
            // 파일 처리를 위한 Board 객체 생성
        Optional<Diagnose> dbDiagnose = diagnoseRepository.findById(id);
        if (dbDiagnose.isPresent()) {
            ((Diagnose)dbDiagnose.get()).setId(id);
            ((Diagnose)dbDiagnose.get()).setName(diagnose.getName());
            ((Diagnose)dbDiagnose.get()).setDate(diagnose.getDate());
            ((Diagnose)dbDiagnose.get()).setImg(diagnose.getImg());
            diagnoseRepository.save((Diagnose)dbDiagnose.get());
            return (Diagnose)dbDiagnose.get();
        } else {
            return null;
        }
    }*/



    public Optional<Diagnose> findById(Long id) {
        Optional<Diagnose> diagnose = diagnoseRepository.findById(id);
        return diagnose;
    }

    public static List<Diagnose> myDiagnoses(String email) {
        List<Diagnose> diagnoses = diagnoseRepository.findByEmail(email);
        return diagnoses;
    }



    public List<Diagnose> findByName(String email) {
        List<Diagnose> diagnoses = new ArrayList();
        diagnoseRepository.findOrderByDate(email).forEach((e) -> {
            diagnoses.add(e);
        });
        return diagnoses;
    }

    public List<Diagnose> findByTag(String email) {
        List<Diagnose> roadmaps = new ArrayList();
        diagnoseRepository.findOrderByName(email).forEach((e) -> {
            roadmaps.add(e);
        });
        return roadmaps;
    }



    public Diagnose deleteDiagnose(int diagnoseId) {
        Long roadmapId = (long)diagnoseId;
        Optional<Diagnose> delRoadmap = diagnoseRepository.findById(roadmapId);
        if (delRoadmap.isPresent()) {
            diagnoseRepository.delete((Diagnose)delRoadmap.get());
            return (Diagnose)delRoadmap.get();
        } else {
            return null;
        }
    }

}


