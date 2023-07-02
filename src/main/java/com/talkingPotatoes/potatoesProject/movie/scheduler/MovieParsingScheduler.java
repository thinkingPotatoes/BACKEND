package com.talkingPotatoes.potatoesProject.movie.scheduler;

import com.talkingPotatoes.potatoesProject.movie.dto.*;
import com.talkingPotatoes.potatoesProject.movie.entity.MovieApi;
import com.talkingPotatoes.potatoesProject.movie.mapper.*;
import com.talkingPotatoes.potatoesProject.movie.repository.*;
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
import java.util.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class MovieParsingScheduler {

    private final MovieApiRepository movieApiRepository;
    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;
    private final PosterRepository posterRepository;
    private final StaffRepository staffRepository;
    private final StillRepository stillRepository;

    private final MovieApiMapper movieApiMapper;
    private final ActorMapper actorMapper;
    private final DirectorMapper directorMapper;
    private final MovieMapper movieMapper;
    private final PosterMapper posterMapper;
    private final StaffMapper staffMapper;
    private final StillMapper stillMapper;

    private List<ActorDto> actorList;
    private List<DirectorDto> directorList;
    private List<MovieDto> movieList;
    private List<PosterDto> posterList;
    private List<StaffDto> staffList;
    private List<StillDto> stillList;

    @Scheduled(cron = "0 0 0 1 * *")
    public void getData() throws Exception{
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        actorList = new ArrayList<>();
        directorList = new ArrayList<>();
        movieList = new ArrayList<>();
        posterList = new ArrayList<>();
        staffList = new ArrayList<>();
        stillList = new ArrayList<>();

        List<MovieApi> movieApiList = movieApiRepository.findAllByUpdatedAt(LocalDate.now());
        List<MovieApiDto> movieApiDtoList = movieApiMapper.toDto(movieApiList);

        for(MovieApiDto movieApiDto : movieApiDtoList) {
            parse(movieApiDto);
        }

        /* 파싱한 데이터 저장하기 */
        actorRepository.saveAll(actorMapper.toEntity((actorList)));
        directorRepository.saveAll(directorMapper.toEntity(directorList));
        movieRepository.saveAll(movieMapper.toEntity(movieList));
        posterRepository.saveAll(posterMapper.toEntity(posterList));
        staffRepository.saveAll(staffMapper.toEntity(staffList));
        stillRepository.saveAll(stillMapper.toEntity(stillList));

        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        log.info("코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());
    }

    public void parse(MovieApiDto movieApiDto) throws Exception{

        JSONParser parser = null;
        StringTokenizer st = null;

        /* 감독 배열 파싱해서 directordto에 데이터 저장하고 list에 dto 담기 */
        parser = new JSONParser();
        JSONObject directors = (JSONObject) parser.parse(movieApiDto.getDirector());
        JSONArray directorArray = (JSONArray) directors.get("director");
        if(directorArray!=null && directorArray.size()>0) {
            for (int j = 0; j < directorArray.size(); j++) {
                JSONObject director = (JSONObject) directorArray.get(j);
                DirectorDto directorDto = DirectorDto.builder()
                        .docId(movieApiDto.getDocId())
                        .directorNm(director.get("directorNm").toString())
                        .directorEnNm(director.get("directorEnNm").toString())
                        .directorId(director.get("directorId").toString())
                        .build();
                directorList.add(directorDto);
            }
        }

        /* 배우 배열 파싱해서 actordto에 데이터 저장하고 list에 dto 담기 */
        parser = new JSONParser();
        JSONObject actors = (JSONObject) parser.parse(movieApiDto.getActor());
        JSONArray actorArray = (JSONArray) actors.get("actor");
        if(actorArray!=null && actorArray.size()>0) {
            for (int j = 0; j < actorArray.size(); j++) {
                JSONObject actor = (JSONObject) actorArray.get(j);
                ActorDto actorDto = ActorDto.builder()
                        .docId(movieApiDto.getDocId())
                        .actorNm(actor.get("actorNm").toString())
                        .actorEnNm(actor.get("actorEnNm").toString())
                        .actorId(actor.get("actorId").toString())
                        .build();
                actorList.add((actorDto));
            }
        }

        /* 스태프 배열 파싱해서 staffdto에 데이터 저장하고 list에 dto 담기 */
        parser = new JSONParser();
        JSONObject staffs = (JSONObject) parser.parse(movieApiDto.getStaffs());
        JSONArray staffArray = (JSONArray) staffs.get("staff");
        if(staffArray!=null && staffArray.size()>0) {
            for (int j = 0; j < staffArray.size(); j++) {
                JSONObject staff = (JSONObject) staffArray.get(j);
                String roleGroup = staff.get("staffRoleGroup").toString();
                if(!roleGroup.equals("감독") && !roleGroup.equals("각본") && !roleGroup.equals("출연")) {
                    continue;
                }
                StaffDto staffDto = StaffDto.builder()
                        .docId(movieApiDto.getDocId())
                        .staffId(staff.get("staffId").toString())
                        .staffNm(staff.get("staffNm").toString())
                        .staffRoleGroup(roleGroup)
                        .staffRole(staff.get("staffRole").toString())
                        .build();
                staffList.add(staffDto);
            }
        }

        /* 포스터 파싱해서 posterdto에 데이터 저장하고 list에 dto 담기 */
        String posters = movieApiDto.getPosterUrl();
        st = new StringTokenizer(posters, "|");
        while(st.hasMoreTokens()) {
            PosterDto posterDto = PosterDto.builder()
                    .docId(movieApiDto.getDocId())
                    .posterUrl(st.nextToken())
                    .build();
            posterList.add(posterDto);
        }

        /* 스틸컷 파싱해서 stilldto에 데이터 저장하고 list에 dto 담기 */
        String stills = movieApiDto.getStillUrl();
        st = new StringTokenizer(stills, "|");
        while(st.hasMoreTokens()) {
            StillDto stillDto = StillDto.builder()
                    .docId(movieApiDto.getDocId())
                    .stillUrl(st.nextToken())
                    .build();
            stillList.add(stillDto);
        }

        /* moviedto에 데이터 저장 */
        MovieDto movieDto = MovieDto.builder()
                .docId(movieApiDto.getDocId())
                .title(movieApiDto.getTitle())
                .titleEng(movieApiDto.getTitleEng())
                .titleOrg(movieApiDto.getTitleOrg())
                .nation(movieApiDto.getNation())
                .company(movieApiDto.getCompany())
                .prodYear(movieApiDto.getProdYear())
                .plot(movieApiDto.getPlot())
                .runtime(movieApiDto.getRuntime())
                .rating(movieApiDto.getRating())
                .genre(movieApiDto.getGenre())
                .repRlsDate(movieApiDto.getRepRlsDate())
                .keywords(movieApiDto.getKeywords())
                .build();

        /* list에 dto 담기 */
        movieList.add(movieDto);
    }
}
