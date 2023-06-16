package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-16T18:02:35+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class MovieMapperImpl implements MovieMapper {

    @Override
    public Movie toEntity(MovieDto movieDto) {
        if ( movieDto == null ) {
            return null;
        }

        Movie.MovieBuilder movie = Movie.builder();

        movie.docId( movieDto.getDocId() );
        movie.title( movieDto.getTitle() );
        movie.titleEng( movieDto.getTitleEng() );
        movie.titleOrg( movieDto.getTitleOrg() );
        movie.directorNm( movieDto.getDirectorNm() );
        movie.directorEnNm( movieDto.getDirectorEnNm() );
        movie.directorId( movieDto.getDirectorId() );
        movie.actorNm( movieDto.getActorNm() );
        movie.actorEnNm( movieDto.getActorEnNm() );
        movie.actorId( movieDto.getActorId() );
        movie.nation( movieDto.getNation() );
        movie.company( movieDto.getCompany() );
        movie.prodYear( movieDto.getProdYear() );
        movie.plot( movieDto.getPlot() );
        movie.runtime( movieDto.getRuntime() );
        movie.rating( movieDto.getRating() );
        movie.genre( movieDto.getGenre() );
        movie.repRlsDate( movieDto.getRepRlsDate() );
        movie.keywords( movieDto.getKeywords() );
        movie.posterUrl( movieDto.getPosterUrl() );
        movie.stillUrl( movieDto.getStillUrl() );

        return movie.build();
    }

    @Override
    public MovieDto toDto(Movie movie) {
        if ( movie == null ) {
            return null;
        }

        MovieDto.MovieDtoBuilder movieDto = MovieDto.builder();

        movieDto.docId( movie.getDocId() );
        movieDto.title( movie.getTitle() );
        movieDto.titleEng( movie.getTitleEng() );
        movieDto.titleOrg( movie.getTitleOrg() );
        movieDto.directorNm( movie.getDirectorNm() );
        movieDto.directorEnNm( movie.getDirectorEnNm() );
        movieDto.directorId( movie.getDirectorId() );
        movieDto.actorNm( movie.getActorNm() );
        movieDto.actorEnNm( movie.getActorEnNm() );
        movieDto.actorId( movie.getActorId() );
        movieDto.nation( movie.getNation() );
        movieDto.company( movie.getCompany() );
        movieDto.prodYear( movie.getProdYear() );
        movieDto.plot( movie.getPlot() );
        movieDto.runtime( movie.getRuntime() );
        movieDto.rating( movie.getRating() );
        movieDto.genre( movie.getGenre() );
        movieDto.repRlsDate( movie.getRepRlsDate() );
        movieDto.keywords( movie.getKeywords() );
        movieDto.posterUrl( movie.getPosterUrl() );
        movieDto.stillUrl( movie.getStillUrl() );

        return movieDto.build();
    }

    @Override
    public List<Movie> toEntity(List<MovieDto> movieDtoList) {
        if ( movieDtoList == null ) {
            return null;
        }

        List<Movie> list = new ArrayList<Movie>( movieDtoList.size() );
        for ( MovieDto movieDto : movieDtoList ) {
            list.add( toEntity( movieDto ) );
        }

        return list;
    }

    @Override
    public List<MovieDto> toDto(List<Movie> movieList) {
        if ( movieList == null ) {
            return null;
        }

        List<MovieDto> list = new ArrayList<MovieDto>( movieList.size() );
        for ( Movie movie : movieList ) {
            list.add( toDto( movie ) );
        }

        return list;
    }
}
