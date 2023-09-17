package com.talkingPotatoes.potatoesProject.user.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    private String userId;
    
    private String password;
}
