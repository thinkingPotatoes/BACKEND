package com.talkingPotatoes.potatoesProject.user.dto.request;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateBlogRequest {
    private String title;
    private List<Genre> genreList;
}
