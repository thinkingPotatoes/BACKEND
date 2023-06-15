package com.talkingPotatoes.potatoesProject.movie.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    private String docId;

    private String title;

    private String titleEng;

    private String titleOrg;

    private String directorNm;

    private String directorEnNm;

    private String directorId;

    private String actorNm;

    private String actorEnNm;

    private String actorId;

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

}
