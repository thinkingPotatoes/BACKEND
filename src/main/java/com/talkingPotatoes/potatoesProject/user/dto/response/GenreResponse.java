package com.talkingPotatoes.potatoesProject.user.dto.response;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GenreResponse {
    private List<Genre> genreList;
}
