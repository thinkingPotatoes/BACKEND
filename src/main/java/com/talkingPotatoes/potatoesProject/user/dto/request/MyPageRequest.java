package com.talkingPotatoes.potatoesProject.user.dto.request;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MyPageRequest {

    @NotBlank(message = "비밀번호는 필수 입력사항입니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력사항입니다.")
    private String nickname;

    @NotBlank(message = "필로그명은 필수 입력사항입니다.")
    private String title;

    private List<Genre> genreList;
}
