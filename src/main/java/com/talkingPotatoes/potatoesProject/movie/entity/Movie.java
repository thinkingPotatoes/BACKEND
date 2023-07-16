package com.talkingPotatoes.potatoesProject.movie.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = @Index(name = "idx_movie", columnList = "docId"))
public class Movie {

    @Id
    private String docId;

    private String title;

    private String titleEng;

    private String titleOrg;

    private String nation;

    private String company;

    private String prodYear;

    @Column(columnDefinition = "TEXT")
    private String plot;

    private String runtime;

    private String rating;

    private String genre;

    private String repRlsDate;

    private String keywords;

}
