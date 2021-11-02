package com.BE.starTroad.service.social;

import com.BE.starTroad.helper.constants.SocialLoginType;
import com.fasterxml.jackson.databind.JsonNode;

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
