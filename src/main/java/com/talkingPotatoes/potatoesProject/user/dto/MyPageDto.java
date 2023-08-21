package com.talkingPotatoes.potatoesProject.user.dto;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class MyPageDto {
    private String userId;
    private String password;
    private String nickname;
    private String title;
    private List<Genre> genreList;
    private List<String> genreNameList;
}
