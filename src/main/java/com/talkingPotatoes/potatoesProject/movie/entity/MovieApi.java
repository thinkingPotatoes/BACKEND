package com.talkingPotatoes.potatoesProject.movie.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class MovieApi {

    @Id
    private String docId;

    private String title;

    private String titleEng;

    private String titleOrg;

    @Column(columnDefinition = "TEXT")
    private String director;

    @Column(columnDefinition = "TEXT")
    private String actor;

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

    @Column(columnDefinition = "TEXT")
    private String posterUrl;

    @Column(columnDefinition = "TEXT")
    private String stillUrl;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String staffs;

    private LocalDate updatedAt;

}
