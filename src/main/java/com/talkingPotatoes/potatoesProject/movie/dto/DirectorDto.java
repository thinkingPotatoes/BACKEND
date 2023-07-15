package com.talkingPotatoes.potatoesProject.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DirectorDto {

    private long id;

    private String docId;

    private String directorNm;

    private String directorEnNm;

    private String directorId;

}
