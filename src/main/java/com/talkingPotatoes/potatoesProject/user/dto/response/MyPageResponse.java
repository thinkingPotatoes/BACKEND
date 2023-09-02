package com.talkingPotatoes.potatoesProject.user.dto.response;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import com.talkingPotatoes.potatoesProject.user.entity.Platform;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MyPageResponse {
    private String userId;

    private String nickname;

    private String title;

    private List<Genre> genreList;

    private Platform platform;
}
