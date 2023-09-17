package com.talkingPotatoes.potatoesProject.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordRequest {
    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    private String password;
}