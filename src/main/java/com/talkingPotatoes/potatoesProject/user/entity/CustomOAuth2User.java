package com.talkingPotatoes.potatoesProject.user.entity;

import jakarta.persistence.Column;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private UUID id;
    private Role role;
    private String userId;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                      Map<String, Object> attributes, String nameAttributeKey,
                      UUID id, Role role, String userId) {
        super(authorities, attributes, nameAttributeKey);
        this.id = id;
        this.role = role;
        this.userId = userId;
    }
}
