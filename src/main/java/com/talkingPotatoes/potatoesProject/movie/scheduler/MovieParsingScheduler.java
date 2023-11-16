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

    @Scheduled(cron = "0 10 * * * *")  // API 배치 돌리고 5분 뒤
    public void getData() throws Exception{
        /* 실행 시간 재는 코드 */
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        /* 오늘 변경된 데이터만 업데이트 */
        List<MovieApi> movieApiList = movieApiRepository.findAllByUpdatedAt(LocalDate.now());
        List<MovieApiDto> movieApiDtoList = movieApiMapper.toDto(movieApiList);

        /* 각 데이터마다 삭제 저장 */
        for(MovieApiDto movieApiDto : movieApiDtoList) {
            parse(movieApiDto);
        }

        /* 실행 시간 재는 코드 */
        /* 10만 데이터 기준 10분 정도 소요 */
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
        log.info("코드 실행 시간 (s): " + stopWatch.getTotalTimeSeconds());
    }

    public void parse(MovieApiDto movieApiDto) throws Exception{

        /* 이미 있는 데이터 삭제 */
        actorRepository.deleteByDocIdInQuery(movieApiDto.getDocId());
        directorRepository.deleteByDocIdInQuery(movieApiDto.getDocId());
        movieRepository.deleteByDocIdInQuery(movieApiDto.getDocId());
        posterRepository.deleteByDocIdInQuery(movieApiDto.getDocId());
        staffRepository.deleteByDocIdInQuery(movieApiDto.getDocId());
        stillRepository.deleteByDocIdInQuery(movieApiDto.getDocId());

        List<ActorDto> actorList = new ArrayList<>();
        List<DirectorDto> directorList = new ArrayList<>();
        List<MovieDto> movieList = new ArrayList<>();
        List<PosterDto> posterList = new ArrayList<>();
        List<StaffDto> staffList = new ArrayList<>();
        List<StillDto> stillList = new ArrayList<>();

        JSONParser parser = null;
        StringTokenizer st = null;

        /* 감독 배열 파싱해서 directordto에 데이터 저장하고 list에 dto 담기 */
        parser = new JSONParser();
        JSONObject directors = (JSONObject) parser.parse(movieApiDto.getDirector());
        JSONArray directorArray = (JSONArray) directors.get("director");
        if(directorArray!=null && directorArray.size()>0) {
            for (int j = 0; j < directorArray.size(); j++) {
                JSONObject director = (JSONObject) directorArray.get(j);
                if(director.get("directorNm").toString().equals("")
                        && director.get("directorEnNm").toString().equals("")
                        && director.get("directorId").toString().equals("")) {
                    continue;
                }
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
                if(actor.get("actorNm").toString().equals("")
                        && actor.get("actorEnNm").toString().equals("")
                        && actor.get("actorId").toString().equals("")) {
                    continue;
                }
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

        /* posterUrl 중에 첫번째 url만 가져오기 */
        String firstPoster = "";
        st = new StringTokenizer(posters, "|");
        if(st.hasMoreTokens()) {
            firstPoster = st.nextToken();
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
                .poster(firstPoster)
                .build();

        /* list에 dto 담기 */
        movieList.add(movieDto);

        /* 파싱한 데이터 저장하기 */
        actorRepository.saveAll(actorMapper.toEntity((actorList)));
        directorRepository.saveAll(directorMapper.toEntity(directorList));
        movieRepository.saveAll(movieMapper.toEntity(movieList));
        posterRepository.saveAll(posterMapper.toEntity(posterList));
        staffRepository.saveAll(staffMapper.toEntity(staffList));
        stillRepository.saveAll(stillMapper.toEntity(stillList));
    }
}
