package com.talkingPotatoes.potatoesProject.blog.dto;

import com.talkingPotatoes.potatoesProject.blog.entity.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BlogDto {
    private UUID userId;
    private String title;
    private List<Genre> genreList;
    private List<String> genreNameList;
}
