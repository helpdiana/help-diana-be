package com.BE.HelpDIANA.service.social;

import com.BE.HelpDIANA.helper.constants.SocialLoginType;

public interface SocialOauth {

    //String getOauthRedirectURL();
    //String requestAccessToken_Info(String code);
    //JsonNode getUserInfo(String authToken);
    String getInfo(String accessToken);
    
    default SocialLoginType type() {
        if (this instanceof GoogleOauth) {
            return SocialLoginType.GOOGLE;
        }
        /*
        else if (this instanceof NaverOauth) {
            return SocialLoginType.NAVER;
        }
        else if (this instanceof  KakaoOauth) {
            return SocialLoginType.KAKAO;
        }*/
        else {
            return null;
        }
    }
}
