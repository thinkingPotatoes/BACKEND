package com.talkingPotatoes.potatoesProject.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StillDto {

    private long id;

    private String docId;

    private String stillUrl;

}
