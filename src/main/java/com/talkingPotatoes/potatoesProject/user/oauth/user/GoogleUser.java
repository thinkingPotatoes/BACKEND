package com.talkingPotatoes.potatoesProject.user.oauth.user;

import lombok.AllArgsConstructor;
import lombok.Data;

// 구글로 액세스 토큰을 보내 받아올 구글에 등록된 사용자 정보
@Data
@AllArgsConstructor
public class GoogleUser {
    private String id;
    private String userId;
}
