package com.talkingPotatoes.potatoesProject.blog.dto.request;

import com.talkingPotatoes.potatoesProject.blog.entity.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UpdateUserGenreRequest {
    private List<Genre> genreList;
}
