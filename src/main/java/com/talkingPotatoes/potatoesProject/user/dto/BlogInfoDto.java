package com.talkingPotatoes.potatoesProject.user.dto;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BlogInfoDto {
    private String title;
    private List<Genre> genreList;
    private List<String> genreNameList;
}
