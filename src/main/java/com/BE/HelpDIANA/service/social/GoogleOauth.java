package com.BE.HelpDIANA.service.social;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONObject;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GoogleOauth implements SocialOauth {

    @Value("https://www.googleapis.com/userinfo/v2/me?access_token=")
    private String GOOGLE_SNS_USER_INFO_BASE_URL;

    /*
    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${sns.google.token.url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;

    @Override
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope","profile%20email");
        params.put("response_type","code");
        params.put("client_id",GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri",GOOGLE_SNS_CALLBACK_URL);
        params.put("access_type","offline");

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        return GOOGLE_SNS_BASE_URL + "?" + parameterString;
    }

    @Override
    public String requestAccessToken_Info(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        System.out.println("code : " + code);
        System.out.println(GOOGLE_SNS_CALLBACK_URL);
        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);
        //Get access token success
        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            //change string to json(access_token, refresh_token)
            String jsonStr = responseEntity.getBody();
            JSONParser parser = new JSONParser();
            JSONObject obj = null;


            try {
                obj = (JSONObject) parser.parse(jsonStr);
            } catch (ParseException e) {
                System.out.println("변환에 실패");
                e.printStackTrace();
            }

            JsonNode userInfo = null;

            String user_AT = obj.get("access_token").toString();
            String user_RT = "";

            try {
                user_RT = obj.get("refresh_token").toString();
            } catch (Exception e) {
                user_RT = "";
            }

            userInfo = getUserInfo(user_AT);


            String user_id = userInfo.get("id").toString();
            String user_name = userInfo.get("name").toString();
            String user_email = userInfo.get("email").toString();

            JSONObject user = new JSONObject();

            user.put("user_id", user_id);
            user.put("user_name", user_name);
            user.put("user_email", user_email);
            user.put("user_AT", user_AT);
            user.put("user_RT", user_RT);

            String userInfoStr = user.toString();

            return userInfoStr;
            //return responseEntity.getBody();
        }
        //구글 로그인 처리 요청 실패
        return null;
    }*/

    public String getInfo(String accessToken) {
        JsonNode userInfo = null;
        userInfo = getUserInfo(accessToken);

        String user_id = userInfo.get("id").toString();
        String user_name = userInfo.get("name").toString();
        String user_email = userInfo.get("email").toString();

        JSONObject user = new JSONObject();

        user.put("user_id", user_id);
        user.put("user_name", user_name);
        user.put("user_email", user_email);

        String userInfoStr = user.toString();

        return userInfoStr;
    }

    public JsonNode getUserInfo(String accessToken) {

        JsonNode userInfo = null;

        //accessToken을 GOOGLE_SNS_USER_INFO_BASE_URL 에 붙여서 redirect 하면 user info json 나옴

        String requestUrl = GOOGLE_SNS_USER_INFO_BASE_URL + accessToken;

        final HttpClient client = HttpClientBuilder.create().build();
        final HttpGet get = new HttpGet(requestUrl);

        get.addHeader("Authorization","Bearer"+accessToken);

        try {
            final HttpResponse response = client.execute(get);
            final int responseCode = response.getStatusLine().getStatusCode();

            ObjectMapper mapper = new ObjectMapper();
            userInfo = mapper.readTree(response.getEntity().getContent());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }

}
