package com.BE.starTroad.test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;

@Service
public class TranslateService extends Thread {

    public static void translatePythonExe(String path){
        //진단서의 path를 python으로 넘기는 함수
        //PythonInterpreter interpreter = new PythonInterpreter();
        //interpreter.execfile("/Users/kimbokyeong/Desktop/develop/ocr_trans.py"); //py파일 실행
        //interpreter.exec("print(addition(7,8))"+path); //수정 path인자 넣어서 python 호출

        Cmd cmd = new Cmd();
        try {
            System.out.println(path);
            Thread.sleep(100);
            String commmand = cmd.inputCommand("python3 /Users/kimbokyeong/Desktop/develop/ocr_trans.py"+" "+path+" "+path);
            Thread.sleep(1000);
            String result = cmd.execCommand(commmand);

            System.out.println(result);
            System.out.println("done");
        } catch (Exception e) {

            System.out.println("error");

        }



    }
    public static void photoOfTrans(Image photo){
        // json translation bf af 저장
        // JSON 파일 읽기
        JSONParser parser = new JSONParser();
        try {

            Object obj1 = parser.parse(new FileReader(photo.getImagePath()+"/ocr_"+photo.getOrigImageName()+".json"));
            Object obj2 = parser.parse(new FileReader(photo.getImagePath()+"/trans_"+photo.getOrigImageName()+".json"));

            JSONObject jsonObject1 = (JSONObject) obj1;
            JSONObject jsonObject2 = (JSONObject) obj2;

            JSONArray jsonTrans_bfList = (JSONArray) jsonObject1.get("trans_before");
            JSONArray jsonTrans_afList = (JSONArray) jsonObject2.get("trans_after");

            ArrayList<String> list_bf = new ArrayList<String>();
            ArrayList<String> list_af = new ArrayList<String>();

            if (jsonTrans_bfList != null) {
                for (int i=0;i<jsonTrans_bfList.size();i++){
                    list_bf.add(jsonTrans_bfList.get(i).toString());
                }
                photo.setTranslate_bf(list_bf);
            }
            if (jsonTrans_afList != null) {
                for (int i=0;i<jsonTrans_afList.size();i++){
                    list_af.add(jsonTrans_afList.get(i).toString());
                }
                photo.setTranslate_af(list_af);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //translation bf json 리스트 리턴
    }
}
