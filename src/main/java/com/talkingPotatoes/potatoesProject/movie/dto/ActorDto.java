package com.talkingPotatoes.potatoesProject.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActorDto {

    private long id;

    private String docId;

    private String actorNm;

    private String actorEnNm;

    private String actorId;

}
