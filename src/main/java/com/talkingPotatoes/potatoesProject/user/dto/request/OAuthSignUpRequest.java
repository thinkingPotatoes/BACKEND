package com.talkingPotatoes.potatoesProject.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OAuthSignUpRequest {

    @NotNull
    private UUID id;

    @NotBlank(message = "닉네임은 필수 입력사항입니다.")
    @Pattern(regexp = "[ㄱ-ㅎ가-힣a-zA-Z0-9]{2,9}",
            message = "닉네임은 한글, 영문, 숫자만 가능하며 2 ~ 10자리까지 가능합니다.")
    private String nickname;

    @NotBlank(message = "블로그명은 필수 입력사항입니다.")
    private String title;

    private List<UUID> genreList;
}
