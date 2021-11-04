package com.BE.HelpDIANA.service;

import com.BE.HelpDIANA.helper.constants.SocialLoginType;
import com.BE.HelpDIANA.service.social.SocialOauth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OauthService {

    //final어야함
    private final List<SocialOauth> socialOauthList;
    private final HttpServletResponse response;

    /*
    public void request(SocialLoginType socialLoginType) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        String redirectURL = socialOauth.getOauthRedirectURL();
        try {
            response.sendRedirect(redirectURL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String requestAccessToken_Info(SocialLoginType socialLoginType, String code) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        return socialOauth.requestAccessToken_Info(code);
    }

    public JsonNode getUserInfo(SocialLoginType socialLoginType, String accessToken) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        return socialOauth.getUserInfo(accessToken);
    }*/

    public String getInfo(SocialLoginType socialLoginType, String accessToken) {
        SocialOauth socialOauth = this.findSocialOauthByType(socialLoginType);
        return socialOauth.getInfo(accessToken);
    }

    private SocialOauth findSocialOauthByType(SocialLoginType socialLoginType) {
        return socialOauthList.stream()
                .filter(x -> x.type() == socialLoginType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("알 수 없는 SocialLoginType 입니다."));
    }
}
