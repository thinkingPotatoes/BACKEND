package com.talkingPotatoes.potatoesProject.movie.service.implement;

import com.talkingPotatoes.potatoesProject.movie.dto.*;
import com.talkingPotatoes.potatoesProject.movie.mapper.*;
import com.talkingPotatoes.potatoesProject.movie.repository.*;
import com.talkingPotatoes.potatoesProject.movie.service.IMovieService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieServiceImpl implements IMovieService {

    private final ActorRepository actorRepository;
    private final DirectorRepository directorRepository;
    private final MovieRepository movieRepository;
    private final PosterRepository posterRepository;
    private final StaffRepository staffRepository;
    private final StillRepository stillRepository;

    private final ActorMapper actorMapper;
    private final DirectorMapper directorMapper;
    private final MovieMapper movieMapper;
    private final PosterMapper posterMapper;
    private final StaffMapper staffMapper;
    private final StillMapper stillMapper;

    @Override
    @Transactional
    public void saveActor(List<ActorDto> actorDtoList) {
        actorRepository.saveAll(actorMapper.toEntity(actorDtoList));
    }

    @Override
    @Transactional
    public void saveDirector(List<DirectorDto> directorDtoList) {
        directorRepository.saveAll(directorMapper.toEntity(directorDtoList));
    }

    @Override
    @Transactional
    public void saveMovie(List<MovieDto> movieDtoList) {
        movieRepository.saveAll(movieMapper.toEntity(movieDtoList));
    }

    @Override
    @Transactional
    public void savePoster(List<PosterDto> posterDtoList) {
        posterRepository.saveAll(posterMapper.toEntity(posterDtoList));
    }

    @Override
    @Transactional
    public void saveStaff(List<StaffDto> staffDtoList) {
        staffRepository.saveAll(staffMapper.toEntity(staffDtoList));
    }

    @Override
    @Transactional
    public void saveStill(List<StillDto> stillDtoList) {
        stillRepository.saveAll(stillMapper.toEntity(stillDtoList));
    }


}
