package com.talkingPotatoes.potatoesProject.movie.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SelectMovieResponse {
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

    private String poster;

    private List<StaffResponse> staffList;

    private List<PosterUrlResponse> posterList;
}
