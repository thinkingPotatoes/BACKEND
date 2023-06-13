package com.talkingPotatoes.potatoesProject.movie;


import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@Entity
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

    @Builder
    public Movie(String docId, String title, String titleEng, String titleOrg, String directorNm, String directorEnNm, String directorId, String actorNm, String actorEnNm, String actorId, String nation, String company, String prodYear, String plot, String runtime, String rating, String genre, String repRlsDate, String keywords, String posterUrl, String stillUrl) {
        this.docId = docId;
        this.title = title;
        this.titleEng = titleEng;
        this.titleOrg = titleOrg;
        this.directorNm = directorNm;
        this.directorEnNm = directorEnNm;
        this.directorId = directorId;
        this.actorNm = actorNm;
        this.actorEnNm = actorEnNm;
        this.actorId = actorId;
        this.nation = nation;
        this.company = company;
        this.prodYear = prodYear;
        this.plot = plot;
        this.runtime = runtime;
        this.rating = rating;
        this.genre = genre;
        this.repRlsDate = repRlsDate;
        this.keywords = keywords;
        this.posterUrl = posterUrl;
        this.stillUrl = stillUrl;
    }

}
