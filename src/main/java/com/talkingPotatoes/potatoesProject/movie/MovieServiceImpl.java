package com.talkingPotatoes.potatoesProject.movie;

import jakarta.persistence.EntityManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MovieServiceImpl implements IMovieService {

    private final EntityManager em;

    public MovieServiceImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void save(String body) throws Exception {
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);
        JSONArray data = (JSONArray) object.get("Data");
        JSONObject dataObj = (JSONObject) data.get(0);
        JSONArray result = (JSONArray) dataObj.get("Result");
        for (int i = 0; i < result.size(); i++) {
            JSONObject movieObj = (JSONObject) result.get(i);

            Movie movie = Movie.builder()
                    .docId((String) movieObj.get("DOCID"))
                    .title((String) movieObj.get("title"))
                    .titleEng((String) movieObj.get("titleEng"))
                    .titleOrg((String) movieObj.get("titleOrg"))
                    //.directorNm((String) movieObj.get("DOCID"))
                    //.directorEnNm((String) movieObj.get("DOCID"))
                    //.directorId((String) movieObj.get("DOCID"))
                    //.actorNm((String) movieObj.get("DOCID"))
                    //.actorEnNm((String) movieObj.get("DOCID"))
                    //.actorId((String) movieObj.get("DOCID"))
                    .nation((String) movieObj.get("nation"))
                    .company((String) movieObj.get("company"))
                    .prodYear((String) movieObj.get("prodYear"))
                    //.plot((String) movieObj.get("DOCID"))
                    .runtime((String) movieObj.get("runtime"))
                    .rating((String) movieObj.get("rating"))
                    .genre((String) movieObj.get("genre"))
                    .repRlsDate((String) movieObj.get("repRlsDate"))
                    .keywords((String) movieObj.get("keywords"))
                    .posterUrl((String) movieObj.get("posters"))
                    .stillUrl((String) movieObj.get("stlls"))
                    .build();

            em.persist(movie);

        }
    }

}
