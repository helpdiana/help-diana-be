package com.diana.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/api/diagnose/ocr"})
public class TranslateController {

   /* @GetMapping({"/{diagnose_id}"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@CurrentUser UserPrincipal userPrincipal, @RequestParam(value="diagnose_id") String diagnose_id) throws Exception {
        String email = userPrincipal.getEmail();
        //진단서의 path(diagnose_id.getPath())를 python으로 넘기는 함수,,,,

        //image list로 이루어진 image의 translation bf af 저장

        //translation bf json 리스트 리턴



        return new ResponseEntity(, HttpStatus.OK);
    }

    @GetMapping({"/highlight/{diagnose_id}"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@CurrentUser UserPrincipal userPrincipal, @RequestParam(value="diagnose_id") String diagnose_id) throws Exception {
        String email = userPrincipal.getEmail();

        //translation Af json 리스트 리턴


        return new ResponseEntity(, HttpStatus.OK);
    }
    @GetMapping({"/{image_id}/{diagnose_id}"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(@CurrentUser UserPrincipal userPrincipal, @RequestParam(value="diagnose_id") String diagnose_id,@RequestParam(value="image_id") String image_id) throws Exception {
        String email = userPrincipal.getEmail();

        //해당 translation af json 한개 리턴



        return new ResponseEntity(, HttpStatus.OK);
    }*/

}

