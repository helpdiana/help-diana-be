package com.BE.HelpDIANA.controller;

import com.BE.HelpDIANA.domain.User;
import com.BE.HelpDIANA.helper.constants.SocialLoginType;
import com.BE.HelpDIANA.service.JpaUserService;
import com.BE.HelpDIANA.service.JwtTokenService;
import com.BE.HelpDIANA.service.OauthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin(origins="*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/auth")
@Slf4j
public class OauthController {

    @Autowired
    private OauthService oauthService;

    @Autowired
    private JpaUserService jpaUserService;

    @Autowired
    private JwtTokenService jwtTokenService;

    /*
    @GetMapping(value = "/{socialLoginType}")
    public void socialLoginType(
            @PathVariable(name = "socialLoginType") SocialLoginType socialLoginType) {
        System.out.println(">> 사용자로부터의 소셜 로그인 요청 : "+socialLoginType);
        try {
            oauthService.request(socialLoginType);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }
    */

    @PostMapping(value = "/{socialLoginType}/token")
    public ResponseEntity<String> getToken(@PathVariable(name="socialLoginType") SocialLoginType socialLoginType,
                                     @RequestBody JSONObject reqbody) throws ParseException {
				     
	String token = reqbody.get("access_token").toString();

        String result = oauthService.getInfo(socialLoginType, token);
        if (result != null){

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(result);
            JSONObject jsonObj = (JSONObject) obj;

            String email = jsonObj.get("user_email").toString();
            String name = jsonObj.get("user_name").toString();
            int length = email.length();
            email = email.substring(1,length-1);
            length = name.length();
            name = name.substring(1,length-1);
            Optional<User> dbUser = jpaUserService.findByEmail(email);

            if (dbUser.isPresent()) {
                System.out.println("Already registered USER");
            }
            else {
                System.out.println("new USER");
                User newUser = new User();
                //이메일이랑 이름 뒤에 "" 떼기
                newUser.setEmail(email);
                newUser.setName(name);
                jpaUserService.save(newUser);
            }

            final String jwtToken = jwtTokenService.createJwtToken(email,name);
            if(jwtToken == null) { //token 생성이 안된 경우
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            else {
                return new ResponseEntity<String>(jwtToken,HttpStatus.OK);
                //return ResponseEntity.ok(new JwtResponse(token));
            }
        }
        else {
            //accessToken 얻지 못함
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
