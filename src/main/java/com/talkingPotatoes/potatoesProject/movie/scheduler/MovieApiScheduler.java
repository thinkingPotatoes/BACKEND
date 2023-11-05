package com.talkingPotatoes.potatoesProject.movie.scheduler;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieApiDto;
import com.talkingPotatoes.potatoesProject.movie.mapper.MovieApiMapper;
import com.talkingPotatoes.potatoesProject.movie.repository.MovieApiRepository;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieApiScheduler {

    private final MovieApiRepository movieApiRepository;
    private final MovieApiMapper movieApiMapper;
    private List<MovieApiDto> movieApiList;
    // 데이터 들어오는 개수 확인, 0개 들어오면(마지막일 때) 반복문 탈출
    private int cnt;

    @Scheduled(cron = "0 45 13 ? * *") // 매주 월요일 새벽 1시(임의) (cron = "0 0 1 ? * MON"), 처음 배치 돌릴 때는 (cron = "0 현재시간에서 1~2분 뒤 * * * *")
    public void getData() throws Exception{
        /* 실행 시간 재는 코드 */
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        movieApiList = new ArrayList<>();

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
            //log.info(Integer.toString(cnt));
            index++;

        }

        /* 파싱한 데이터 저장하기 */
        movieApiRepository.saveAll(movieApiMapper.toEntity(movieApiList));

        /* 실행 시간 재는 코드 */
        /* 10만 데이터 기준 5분 미만 소요 */
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

        /* Result Array -> MovieApiDto List */
        for (int i = 0; i < result.size(); i++) {
            JSONObject movieObj = (JSONObject) result.get(i);

            /* 최종 업데이트 날짜 비교 */
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate modDate = LocalDate.parse(movieObj.get("modDate").toString(), formatter);
            if(modDate.isBefore(LocalDate.now()) && movieApiRepository.existsById(movieObj.get("DOCID").toString())) {
                continue;
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

            /* MovieApiDto에 데이터 저장 */
            MovieApiDto movieApiDto = MovieApiDto.builder()
                    .docId(movieObj.get("DOCID").toString())
                    .title(movieObj.get("title").toString())
                    .titleEng(movieObj.get("titleEng").toString())
                    .titleOrg(movieObj.get("titleOrg").toString())
                    .director(movieObj.get("directors").toString())
                    .actor(movieObj.get("actors").toString())
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
                    .staffs(movieObj.get("staffs").toString())
                    .updatedAt(LocalDate.now())
                    .build();

            //log.info(movieApiDto.toString());

            /* list에 dto 담기 */
            movieApiList.add(movieApiDto);

        }
    }
}
