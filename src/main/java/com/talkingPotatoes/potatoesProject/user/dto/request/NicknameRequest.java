package com.talkingPotatoes.potatoesProject.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NicknameRequest {
    @NotBlank(message = "닉네임은 필수 입력사항입니다.")
    private String nickname;
}
