package com.talkingPotatoes.potatoesProject.movie.scheduler;

import com.talkingPotatoes.potatoesProject.movie.dto.BoxOfficeRateDto;
import com.talkingPotatoes.potatoesProject.movie.mapper.BoxOfficeRateMapper;
import com.talkingPotatoes.potatoesProject.movie.repository.BoxOfficeRateRepository;
import com.talkingPotatoes.potatoesProject.movie.repository.MovieRepository;
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
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class BoxOfficeApiScheduler {
    private final BoxOfficeRateRepository boxOfficeRateRepository;
    private final BoxOfficeRateMapper boxOfficeRateMapper;

    private List<BoxOfficeRateDto> boxOfficeRateList;

    @Scheduled(cron = "0 10 14 ? * *")
    public void getData() throws Exception{
        /* 실행 시간 재는 코드 */
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        boxOfficeRateList = new ArrayList<>();

        /* 전일자 조회 */
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar date = new GregorianCalendar(Locale.KOREA);
        date.add(Calendar.DATE, -1);

        /* open API */
        StringBuilder urlBuilder = new StringBuilder(
                "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?");
        urlBuilder.append("&" + URLEncoder.encode("key", "UTF-8") + "=7031e50a6e52b5ea43005b83ae35caa7");
        urlBuilder.append("&" + URLEncoder.encode("targetDt", "UTF-8") + "=" + simpleDateFormat.format(date.getTime()));
        log.info(urlBuilder.toString());

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        log.info("Response code: " + conn.getResponseCode());

        /* json parsing */
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

        String targetDt = simpleDateFormat.format(date.getTime()).toString();
        parse(sb.toString(), targetDt);

        boxOfficeRateRepository.saveAll(boxOfficeRateMapper.toEntity(boxOfficeRateList));

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        log.info("코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());
    }

    public void parse(String body, String targetDt) throws Exception {
        StringTokenizer st = null;

        /* Result Array 파싱 */
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(body);
        JSONObject data = (JSONObject) object.get("boxOfficeResult");
        JSONArray result = (JSONArray) data.get("dailyBoxOfficeList");

        for (int i = 0; i < 10; i++) {
            JSONObject boxOfficeObj = (JSONObject) result.get(i);

            /* Get movie Poster and movieId */
            ArrayList<String> movieData = getMovieData(boxOfficeObj.get("movieNm").toString(), boxOfficeObj.get("openDt").toString().replaceAll("-",""));

            /* BoxOfficeDto 데이터 저장 */
            BoxOfficeRateDto boxOfficeRateDto = BoxOfficeRateDto.builder()
                    .rate(Integer.parseInt(boxOfficeObj.get("rank").toString()))
                    .movieId(movieData.get(1))
                    .movieNm(boxOfficeObj.get("movieNm").toString())
                    .targetDt(targetDt)
                    .posterUrl(movieData.get(0))
                    .audiAcc(boxOfficeObj.get("audiAcc").toString())
                    .build();

            boxOfficeRateList.add(boxOfficeRateDto);
        }
    }
   public ArrayList<String> getMovieData(String movieNm, String releaseDt) throws Exception{
       /* open api 샘플코드 */
       /* StringBuilder에 json 데이터 line으로 읽어서 저장 */
       StringBuilder urlBuilder = new StringBuilder(
               "http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&detail=Y");
       urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=6HD78WK5N6X4BSJYO374");
       urlBuilder.append("&" + URLEncoder.encode("title", "UTF-8") + "=" + URLEncoder.encode(movieNm, "UTF-8"));
       urlBuilder.append("&" + URLEncoder.encode("releaseDts", "UTF-8") + "=" + URLEncoder.encode(releaseDt, "UTF-8"));

       URL url = new URL(urlBuilder.toString());
       HttpURLConnection conn = (HttpURLConnection) url.openConnection();
       conn.setRequestMethod("GET");
       conn.setRequestProperty("Content-type", "application/json");

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

       /* 영화정보 담겨있는 Result Array 파싱 */
       JSONParser parser = new JSONParser();
       JSONObject object = (JSONObject) parser.parse(sb.toString());
       JSONArray data = (JSONArray) object.get("Data");
       JSONObject dataObj = (JSONObject) data.get(0);
       JSONArray result = (JSONArray) dataObj.get("Result");
       JSONObject movieObj = (JSONObject) result.get(0);

       ArrayList<String> datas = new ArrayList<>();

       String posters = movieObj.get("posters").toString();
       String firstPoster = "";
       StringTokenizer st = new StringTokenizer(posters, "|");
       if(st.hasMoreTokens()) {
           firstPoster = st.nextToken();
       }

       datas.add(firstPoster);
       datas.add(movieObj.get("DOCID").toString());

       return datas;
   }
}
