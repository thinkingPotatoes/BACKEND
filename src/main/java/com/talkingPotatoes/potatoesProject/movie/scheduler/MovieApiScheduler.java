package com.talkingPotatoes.potatoesProject.movie.scheduler;

import com.talkingPotatoes.potatoesProject.movie.dto.*;
import com.talkingPotatoes.potatoesProject.movie.service.IMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieApiScheduler {

    private final IMovieService movieService;

    private List<ActorDto> actorList;
    private List<DirectorDto> directorList;
    private List<MovieDto> movieList;
    private List<PosterDto> posterList;
    private List<StaffDto> staffList;
    private List<StillDto> stillList;
    private int cnt;

    @Scheduled(cron = "0 19 * * * *")
    public void getData() throws Exception{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        actorList = new ArrayList<>();
        directorList = new ArrayList<>();
        movieList = new ArrayList<>();
        posterList = new ArrayList<>();
        staffList = new ArrayList<>();
        stillList = new ArrayList<>();

        cnt = 1;
        int index = 0;
        while(cnt>0) {
            /* open api 샘플코드 */
            /* StringBuilder에 json 데이터 line으로 읽어서 저장 */
            StringBuilder urlBuilder = new StringBuilder(
                    "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2");
            urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=6HD78WK5N6X4BSJYO374");
            urlBuilder.append("&" + URLEncoder.encode("listCount", "UTF-8") + "=100");
            urlBuilder.append("&" + URLEncoder.encode("startCount", "UTF-8") + "="+100*index);
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

            /* json 파싱해서 MovieDto 리스트, StaffDto 리스트로 변환 */
            parse(sb.toString());
            log.info(Integer.toString(cnt));
            index++;

        }

        /* 파싱한 데이터 service로 넘기기 */
        movieService.saveActor(actorList);
        movieService.saveDirector(directorList);
        movieService.saveMovie(movieList);
        movieService.savePoster(posterList);
        movieService.saveStaff(staffList);
        movieService.saveStill(stillList);

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        log.info("코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());
    }

    public void parse(String body) throws Exception{

        StringTokenizer st = null;

        /* 영화정보 담겨있는 Result Array 파싱 */
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);
        JSONArray data = (JSONArray) object.get("Data");
        JSONObject dataObj = (JSONObject) data.get(0);
        cnt = Integer.parseInt(dataObj.get("Count").toString());
        JSONArray result = (JSONArray) dataObj.get("Result");

        /* Result Array -> MovieDto List */
        for (int i = 0; i < result.size(); i++) {
            JSONObject movieObj = (JSONObject) result.get(i);

            /* 감독 배열 파싱해서 directordto에 데이터 저장하고 list에 dto 담기 */
            JSONObject directors = (JSONObject) movieObj.get("directors");
            JSONArray directorArray = (JSONArray) directors.get("director");
            if(directorArray!=null && directorArray.size()>0) {
                for (int j = 0; j < directorArray.size(); j++) {
                    JSONObject director = (JSONObject) directorArray.get(j);
                    DirectorDto directorDto = DirectorDto.builder()
                            .docId(movieObj.get("DOCID").toString())
                            .directorNm(director.get("directorNm").toString())
                            .directorEnNm(director.get("directorEnNm").toString())
                            .directorId(director.get("directorId").toString())
                            .build();
                    directorList.add(directorDto);
                }
            }

            /* 배우 배열 파싱해서 actordto에 데이터 저장하고 list에 dto 담기 */
            JSONObject actors = (JSONObject) movieObj.get("actors");
            JSONArray actorArray = (JSONArray) actors.get("actor");
            if(actorArray!=null && actorArray.size()>0) {
                for (int j = 0; j < actorArray.size(); j++) {
                    JSONObject actor = (JSONObject) actorArray.get(j);
                    ActorDto actorDto = ActorDto.builder()
                            .docId(movieObj.get("DOCID").toString())
                            .actorNm(actor.get("actorNm").toString())
                            .actorEnNm(actor.get("actorEnNm").toString())
                            .actorId(actor.get("actorId").toString())
                            .build();
                    actorList.add((actorDto));
                }
            }


            /* 포스터 파싱해서 posterdto에 데이터 저장하고 list에 dto 담기 */
            String posters = movieObj.get("posters").toString();
            st = new StringTokenizer(posters, "|");
            while(st.hasMoreTokens()) {
                PosterDto posterDto = PosterDto.builder()
                        .docId(movieObj.get("DOCID").toString())
                        .posterUrl(st.nextToken())
                        .build();
                posterList.add(posterDto);
            }

            /* 스틸컷 파싱해서 stilldto에 데이터 저장하고 list에 dto 담기 */
            String stills = movieObj.get("stlls").toString();
            st = new StringTokenizer(stills, "|");
            while(st.hasMoreTokens()) {
                StillDto stillDto = StillDto.builder()
                        .docId(movieObj.get("DOCID").toString())
                        .stillUrl(st.nextToken())
                        .build();
                stillList.add(stillDto);
            }

            /* 스태프 배열 파싱해서 staffdto에 데이터 저장하고 list에 dto 담기 */
            JSONObject staffs = (JSONObject) movieObj.get("staffs");
            JSONArray staffArray = (JSONArray) staffs.get("staff");
            if(staffArray!=null && staffArray.size()>0) {
                for (int j = 0; j < staffArray.size(); j++) {
                    JSONObject staff = (JSONObject) staffArray.get(j);
                    String roleGroup = staff.get("staffRoleGroup").toString();
                    if(!roleGroup.equals("감독") && !roleGroup.equals("각본") && !roleGroup.equals("출연")) {
                        continue;
                    }
                    StaffDto staffDto = StaffDto.builder()
                            .docId(movieObj.get("DOCID").toString())
                            .staffId(staff.get("staffId").toString())
                            .staffNm(staff.get("staffNm").toString())
                            .staffRoleGroup(roleGroup)
                            .staffRole(staff.get("staffRole").toString())
                            .build();
                    staffList.add(staffDto);
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

            /* moviedto에 데이터 저장 */
            MovieDto movieDto = MovieDto.builder()
                    .docId(movieObj.get("DOCID").toString())
                    .title(movieObj.get("title").toString())
                    .titleEng(movieObj.get("titleEng").toString())
                    .titleOrg(movieObj.get("titleOrg").toString())
                    .nation(movieObj.get("nation").toString())
                    .company(movieObj.get("company").toString())
                    .prodYear(movieObj.get("prodYear").toString())
                    .plot(plot)
                    .runtime(movieObj.get("runtime").toString())
                    .rating(movieObj.get("rating").toString())
                    .genre(movieObj.get("genre").toString())
                    .repRlsDate(movieObj.get("repRlsDate").toString())
                    .keywords(movieObj.get("keywords").toString())
                    .build();

            /* list에 dto 담기 */
            movieList.add(movieDto);

        }
    }
}
