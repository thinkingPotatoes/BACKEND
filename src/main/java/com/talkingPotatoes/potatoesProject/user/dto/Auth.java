package com.talkingPotatoes.potatoesProject.user.dto;

import com.talkingPotatoes.potatoesProject.user.entity.Role;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
    private UUID id;

    private Role role;

    public Auth(Claims claims) {
        this.id = UUID.fromString(claims.get("id").toString());
        this.role = Role.valueOf(claims.get("role").toString());
    }
}
