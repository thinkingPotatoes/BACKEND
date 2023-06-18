package com.talkingPotatoes.potatoesProject.movie.entity;

import jakarta.persistence.Column;
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

    @Column(length = 50000)
    private String directorNm;

    @Column(length = 50000)
    private String directorEnNm;

    @Column(length = 50000)
    private String directorId;

    @Column(length = 50000)
    private String actorNm;

    @Column(length = 50000)
    private String actorEnNm;

    @Column(length = 50000)
    private String actorId;

    private String nation;

    private String company;

    private String prodYear;

    @Column(length = 50000)
    private String plot;

    private String runtime;

    private String rating;

    private String genre;

    private String repRlsDate;

    private String keywords;

    @Column(length = 50000)
    private String posterUrl;

    @Column(length = 50000)
    private String stillUrl;

}
