package com.talkingPotatoes.potatoesProject.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
@Builder
public class OAuth2Attributes {
    private Map<String, Object> attributes;
    private String id;
    private String nameAttributeKey;
    private String provider;

    public static OAuth2Attributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        OAuth2Attributes result = null;

        switch (registrationId) {
            case "naver":
                result = ofNaver("id", attributes);
                break;
            case "kakao":
                result = ofKakao("id", attributes);
                break;
            default:
                result = ofGoogle(userNameAttributeName, attributes);
        }

        return result;
    }

    private static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .provider("Google")
                .id(attributes.get("sub").toString())
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuth2Attributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attributes.builder()
                .provider("Naver")
                .id(response.get("id").toString())
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {

        return OAuth2Attributes.builder()
                .provider("Kakao")
                .id(attributes.get("id").toString())
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

}
