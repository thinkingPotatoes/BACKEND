package com.talkingPotatoes.potatoesProject.user.dto;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import com.talkingPotatoes.potatoesProject.user.entity.Platform;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MyPageDto {
    private String userId;
    private String password;
    private String nickname;
    private String title;
    private List<Genre> genreList;
    private List<String> genreNameList;
    private Platform platform;
}
