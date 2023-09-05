package com.talkingPotatoes.potatoesProject.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlogUserDto {
    private String nickname;

    private String title;
}
