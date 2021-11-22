package com.BE.HelpDIANA.test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;

@Service
public class TranslateService extends Thread {

    public static void ocrPythonExe(String path){
        Cmd cmd = new Cmd();
        try {
            System.out.println(path);
            Thread.sleep(100);
            String commmand = cmd.inputCommand("python3 /Users/kimbokyeong/Desktop/develop/ocr_v3.py"+" "+path);
            Thread.sleep(1000);
            String result = cmd.execCommand(commmand);
            System.out.println(result);
            System.out.println("done");
        } catch (Exception e) {
            System.out.println("error");
        }
    }

    public static void ocrToEngPythonExe(String path){
        Cmd cmd = new Cmd();
        try {
            System.out.println(path);
            Thread.sleep(100);
            String commmand = cmd.inputCommand("python3 /Users/kimbokyeong/Desktop/develop/trans_to_eng_v5.py"+" "+path+" "+"ocr_total.json");
            Thread.sleep(1000);
            String result = cmd.execCommand(commmand);
            System.out.println(result);
            System.out.println("done");
        } catch (Exception e) {
            System.out.println("error");
        }
    }
    public static void EngToKorPythonExe(String path) throws Exception{

        Cmd cmd = new Cmd();
        Cmd cmd1 = new Cmd();

        Runnable task = new Runnable() {
            public void run() {
                String commmand = cmd.inputCommand("python3 /Users/kimbokyeong/Desktop/develop/trans_to_ko_v5.py"+" "+path+" "+"eng_trans_ocr_total.json");
                String result = cmd.execCommand(commmand);
                System.out.println("done 1 "+result);
            }
        };

        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                String commmand1 = cmd1.inputCommand("ls"); //여기다가 py 돌리면됨.
                String result1 = cmd1.execCommand(commmand1);
                System.out.println("done 2 "+result1);
            }
        };
        Thread subTread1 = new Thread(task);
        Thread subTread2 = new Thread(task1);
        subTread1.start();
        subTread2.start();

        /*try {
            System.out.println(path);
            Thread.sleep(100);
            String commmand = cmd.inputCommand("python3 /Users/kimbokyeong/Desktop/develop/trans_to_ko_v5.py"+" "+path+" "+"eng_trans_ocr_total.json");

            Thread.sleep(1000);

            String commmand1 = cmd1.inputCommand("ls"); //여기다가 py 돌리면됨.
            Thread.sleep(1000);

            String result = cmd.execCommand(commmand);
            String result1 = cmd1.execCommand(commmand1);


            System.out.println(result);
            System.out.println(result1);
            System.out.println("done");
        } catch (Exception e) {
            System.out.println("error");
        }*/
    }

    public static void translatePythonExe(String path){
        Cmd cmd = new Cmd();
        try {
            System.out.println(path);
            Thread.sleep(100);
            System.out.println(path+"/ocr_total.json");
            String commmand = cmd.inputCommand("python3 /Users/kimbokyeong/Desktop/develop/trans_v3.py"+" "+path+" "+"ocr_total.json");
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
    public static void imageOfOcr(Image photo){
        // json translation bf af 저장
        // JSON 파일 읽기
        JSONParser parser = new JSONParser();
        try {

            Object obj1 = parser.parse(new FileReader(photo.getImagePath()+"/ocr_"+photo.getOrigImageName()+".json"));
            //Object obj2 = parser.parse(new FileReader(photo.getImagePath()+"/trans_"+photo.getOrigImageName()+".json"));

            JSONObject jsonObject1 = (JSONObject) obj1;
            //JSONObject jsonObject2 = (JSONObject) obj2;

            JSONArray jsonTrans_bfList = (JSONArray) jsonObject1.get("trans_before");
            //JSONArray jsonTrans_afList = (JSONArray) jsonObject2.get("trans_after");

            ArrayList<String> list_bf = new ArrayList<String>();
            //ArrayList<String> list_af = new ArrayList<String>();


            if (jsonTrans_bfList != null) {
                for (int i=0;i<jsonTrans_bfList.size();i++){
                    list_bf.add(jsonTrans_bfList.get(i).toString());
                }
                photo.setTranslate_bf(list_bf);
            }
            /*if (jsonTrans_afList != null) {
                for (int i=0;i<jsonTrans_afList.size();i++){
                    list_af.add(jsonTrans_afList.get(i).toString());
                }
                photo.setTranslate_af(list_af);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        //translation bf json 리스트 리턴
    }
    public static void imageOfTrans(Image photo){
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
