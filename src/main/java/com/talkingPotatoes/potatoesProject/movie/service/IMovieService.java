package com.talkingPotatoes.potatoesProject.movie.service;

import com.talkingPotatoes.potatoesProject.movie.dto.*;

import java.util.List;

public interface IMovieService {

    public void saveActor(List<ActorDto> actorDtoList);

    public void saveDirector(List<DirectorDto> directorDtoList);

    public void saveMovie(List<MovieDto> movieDtoList);

    public void savePoster(List<PosterDto> posterDtoList);

    public void saveStaff(List<StaffDto> staffDtoList);

    public void saveStill(List<StillDto> stillDtoList);

}
