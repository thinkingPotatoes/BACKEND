package com.talkingPotatoes.potatoesProject.movie.scheduler;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.service.IMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieApiScheduler {

    private final IMovieService movieService;

    @Scheduled(cron = "10 * * * * *")
    public void getData() throws Exception{
        /* open api 샘플코드 */
        /* StringBuilder에 json 데이터 line으로 읽어서 저장 */
        StringBuilder urlBuilder = new StringBuilder(
                "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=6HD78WK5N6X4BSJYO374");
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        /* 데이터 잘 들어오나 확인 */
        log.info(sb.toString());

        /* json 파싱해서 MovieDto 리스트로 변환 */
        log.info(parse(sb.toString()).toString());
    }

    public List<MovieDto> parse(String body) throws Exception{

        List<MovieDto> list = new ArrayList<>();

        /* 영화정보 담겨있는 Result Array 파싱 */
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);
        JSONArray data = (JSONArray) object.get("Data");
        JSONObject dataObj = (JSONObject) data.get(0);
        JSONArray result = (JSONArray) dataObj.get("Result");

        /* Result Array -> MovieDto List */
        for (int i = 0; i < result.size(); i++) {
            JSONObject movieObj = (JSONObject) result.get(i);

            MovieDto movieDto = MovieDto.builder()
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

            list.add(movieDto);
        }

        return list;
    }
}
