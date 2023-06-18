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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieApiScheduler {

    private final IMovieService movieService;

    @Scheduled(cron = "0 36 * * * *")
    public void getData() throws Exception{
        int cnt = 1;
        int index = 0;
        while(cnt>0) {
            /* open api 샘플코드 */
            /* StringBuilder에 json 데이터 line으로 읽어서 저장 */
            StringBuilder urlBuilder = new StringBuilder(
                    "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
            urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=6HD78WK5N6X4BSJYO374");
            urlBuilder.append("&" + URLEncoder.encode("listCount", "UTF-8") + "=400");
            urlBuilder.append("&" + URLEncoder.encode("startCount", "UTF-8") + "="+400*index);
            log.info(urlBuilder.toString());
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            log.info("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            /* 데이터 잘 들어오나 확인 */
            //log.info(sb.toString());

            /* json 파싱해서 MovieDto 리스트로 변환 */
            List<MovieDto> dtolist = parse(sb.toString());
            cnt = count(sb.toString());
            log.info(Integer.toString(cnt));
            index++;
            //log.info(dtolist.toString());

            /* 파싱한 데이터 service로 넘기기 */
            movieService.save(dtolist);
        }
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

            /* 감독 배열 파싱해서 String으로 저장 */
            String directorNm = "";
            String directorEnNm = "";
            String directorId = "";

            JSONObject directors = (JSONObject) movieObj.get("directors");
            JSONArray directorArray = (JSONArray) directors.get("director");
            if(directorArray!=null && directorArray.size()>0) {
                JSONObject director = (JSONObject) directorArray.get(0);
                directorNm = director.get("directorNm").toString();
                directorEnNm = director.get("directorEnNm").toString();
                directorId = director.get("directorId").toString();

                for(int j=1; j<directorArray.size(); j++) {
                    director = (JSONObject) directorArray.get(j);
                    directorNm += ", " + director.get("directorNm");
                    directorEnNm += ", " + director.get("directorEnNm");
                    directorId += ", " + director.get("directorId");
                }
            }

            /* 배우 배열 파싱해서 String으로 저장 */
            String actorNm = "";
            String actorEnNm = "";
            String actorId = "";

            JSONObject actors = (JSONObject) movieObj.get("actors");
            JSONArray actorArray = (JSONArray) actors.get("actor");
            if(actorArray!=null && actorArray.size()>0) {
                JSONObject actor = (JSONObject) actorArray.get(0);
                actorNm = actor.get("actorNm").toString();
                actorEnNm = actor.get("actorEnNm").toString();
                actorId = actor.get("actorId").toString();

                for(int j=1; j<actorArray.size(); j++) {
                    actor = (JSONObject) actorArray.get(j);
                    actorNm += ", " + actor.get("actorNm");
                    actorEnNm += ", " + actor.get("actorEnNm");
                    actorId += ", " + actor.get("actorId");
                }
            }

            /* 줄거리 배열 파싱해서 한국어 줄거리 저장, 한국어 없으면 영어 */
            Map<String, String> plotMap = new HashMap<>();
            String plot = "";

            JSONObject plots = (JSONObject) movieObj.get("plots");
            JSONArray plotArray = (JSONArray) plots.get("plot");
            for(int j=0; j<plotArray.size(); j++) {
                JSONObject plotData = (JSONObject) plotArray.get(j);
                plotMap.put(plotData.get("plotLang").toString(), plotData.get("plotText").toString());
            }

            if(plotMap.containsKey("한국어")) {
                plot = plotMap.get("한국어");
            }
            else if(plotMap.containsKey("영어")) {
                plot = plotMap.get("영어");
            }

            /* dto에 데이터 저장 */
            MovieDto movieDto = MovieDto.builder()
                    .docId(movieObj.get("DOCID").toString())
                    .title(movieObj.get("title").toString())
                    .titleEng(movieObj.get("titleEng").toString())
                    .titleOrg(movieObj.get("titleOrg").toString())
                    .directorNm(directorNm)
                    .directorEnNm(directorEnNm)
                    .directorId(directorId)
                    .actorNm(actorNm)
                    .actorEnNm(actorEnNm)
                    .actorId(actorId)
                    .nation(movieObj.get("nation").toString())
                    .company(movieObj.get("company").toString())
                    .prodYear(movieObj.get("prodYear").toString())
                    .plot(plot)
                    .runtime(movieObj.get("runtime").toString())
                    .rating(movieObj.get("rating").toString())
                    .genre(movieObj.get("genre").toString())
                    .repRlsDate(movieObj.get("repRlsDate").toString())
                    .keywords(movieObj.get("keywords").toString())
                    .posterUrl(movieObj.get("posters").toString())
                    .stillUrl(movieObj.get("stlls").toString())
                    .build();

            /* list에 dto 담기 */
            list.add(movieDto);
        }

        return list;
    }

    public int count(String body) throws Exception{
        /* 영화정보 담겨있는 Result 개수 파싱 */
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);
        JSONArray data = (JSONArray) object.get("Data");
        JSONObject dataObj = (JSONObject) data.get(0);
        int count = Integer.parseInt(dataObj.get("Count").toString());

        return count;
    }
}
