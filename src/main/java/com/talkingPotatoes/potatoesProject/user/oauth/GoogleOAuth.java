package com.talkingPotatoes.potatoesProject.user.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleOAuth implements SocialOAuth {

    private final String googleLoginUrl = "https://accounts.google.com";
    private final String googleTokenRequestUrl = "https://oauth2.googleapis.com/token";
    private final String googleUserInfoRequestUrl = "https://www.googleapis.com/oauth2/v1/userinfo";
    @Value("${app.google.clientId}")
    private String googleClientId;
    @Value("${app.google.redirect}")
    private String googleRedirectUrl;
    @Value("${app.google.secret}")
    private String googleClientSecret;
    @Value("${app..google.scope}")
    private String googleDataAccessScope;
    private final ObjectMapper objectMapper;

    @Override
    public String getOauthRedirectURL() {

        Map<String, Object> params = new HashMap<>();
        params.put("scope", googleDataAccessScope);
        params.put("response_type", "code");
        params.put("client_id", googleClientId);
        params.put("redirect_uri", googleRedirectUrl);

        //parameter를 형식에 맞춰 구성해주는 함수
        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));
        String redirectURL = googleLoginUrl + "?" + parameterString;
        System.out.println("redirectURL = " + redirectURL);
        return googleRedirectUrl;
    }
}
