package com.talkingPotatoes.potatoesProject.movie.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MovieDto {

    private String docId;

    private String title;

    private String titleEng;

    private String titleOrg;

    private String nation;

    private String company;

    private String prodYear;

    private String plot;

    private String runtime;

    private String rating;

    private String genre;

    private String repRlsDate;

    private String keywords;

}
