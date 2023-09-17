package com.talkingPotatoes.potatoesProject.user.dto.request;

import com.talkingPotatoes.potatoesProject.user.entity.Genre;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class MyPageRequest {
    private String nickname;

    private String title;

    private List<Genre> genreList;
}
