package com.BE.starTroad.test;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
public class ImageHandler {
    @Autowired
    private static ImageRepository imageRepository;

    public List<Image> parseFileInfo(Diagnose diagnose, List<MultipartFile> multipartFiles) throws Exception {
        System.out.println("parseFileInfo");
        // 반환할 파일 리스트
        List<Image> fileList = new ArrayList<>();
        // 전달되어 온 파일이 존재할 경우
        if(!CollectionUtils.isEmpty(multipartFiles)) {
            System.out.println("파일 존재");

            // 파일명을 업로드 한 날짜로 변환하여 저장
            //LocalDateTime now = LocalDateTime.now();
            //DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            //String current_date = now.format(dateTimeFormatter);

            //Paths.get("C:", "Desktop", "develop", email, this.created).toString();

            // 다중 파일 처리
            for(MultipartFile multipartFile : multipartFiles) {

                // 파일의 확장자 추출
                String originalFileExtension;
                String contentType = multipartFile.getContentType();

                // 확장자명이 존재하지 않을 경우 처리 x
                if(ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {  // 확장자가 jpeg, png인 파일들만 받아서 처리
                    if(contentType.contains("image/jpeg"))
                        originalFileExtension = ".jpg";
                    else if(contentType.contains("image/png"))
                        originalFileExtension = ".png";
                    else  // 다른 확장자일 경우 처리 x
                        break;
                }

                // 파일명 중복 피하고자 나노초까지 얻어와 지정
                String new_file_name = System.nanoTime() + originalFileExtension;

                /*// 파일 DTO 생성
                PhotoDto photoDto = PhotoDto.builder()
                        .origFileName(multipartFile.getOriginalFilename())
                        .filePath(path + File.separator + new_file_name)
                        .fileSize(multipartFile.getSize())
                        .build();*/


                // 파일 DTO 이용하여 Photo 엔티티 생성
                Image image = new Image(diagnose.getId(),new_file_name, multipartFile.getSize(), diagnose.getFilePath());
                //imageRepository.save(image);

                // 생성 후 리스트에 추가
                fileList.add(image);

                // 업로드 한 파일 데이터를 지정한 파일에 저장 로컬저장
                File file = new File(diagnose.getFilePath() + File.separator + new_file_name);
                multipartFile.transferTo(file);

                //FileCopyUtils.copy(file.getBytes(), new File(new_file_name));

                // 파일 권한 설정(쓰기, 읽기)
                file.setWritable(true);
                file.setReadable(true);
            }
            return fileList;
        }
        else {
            return new ArrayList<>();
        }

    }
}

