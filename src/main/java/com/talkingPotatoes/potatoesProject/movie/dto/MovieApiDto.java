package com.talkingPotatoes.potatoesProject.movie.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MovieApiDto {

    private String docId;

    private String title;

    private String titleEng;

    private String titleOrg;

    private String director;

    private String actor;

    private String nation;

    private String company;

    private String prodYear;

    private String plot;

    private String runtime;

    private String rating;

    private String genre;

    private String repRlsDate;

    private String keywords;

    private String posterUrl;

    private String stillUrl;

    private String staffs;

    private LocalDate updatedAt;

}
