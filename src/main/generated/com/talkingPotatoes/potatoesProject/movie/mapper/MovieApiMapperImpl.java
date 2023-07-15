package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.MovieApiDto;
import com.talkingPotatoes.potatoesProject.movie.entity.MovieApi;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-07-08T15:31:33+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class MovieApiMapperImpl implements MovieApiMapper {

    @Override
    public MovieApi toEntity(MovieApiDto movieApiDto) {
        if ( movieApiDto == null ) {
            return null;
        }

        MovieApi.MovieApiBuilder movieApi = MovieApi.builder();

        movieApi.docId( movieApiDto.getDocId() );
        movieApi.title( movieApiDto.getTitle() );
        movieApi.titleEng( movieApiDto.getTitleEng() );
        movieApi.titleOrg( movieApiDto.getTitleOrg() );
        movieApi.director( movieApiDto.getDirector() );
        movieApi.actor( movieApiDto.getActor() );
        movieApi.nation( movieApiDto.getNation() );
        movieApi.company( movieApiDto.getCompany() );
        movieApi.prodYear( movieApiDto.getProdYear() );
        movieApi.plot( movieApiDto.getPlot() );
        movieApi.runtime( movieApiDto.getRuntime() );
        movieApi.rating( movieApiDto.getRating() );
        movieApi.genre( movieApiDto.getGenre() );
        movieApi.repRlsDate( movieApiDto.getRepRlsDate() );
        movieApi.keywords( movieApiDto.getKeywords() );
        movieApi.posterUrl( movieApiDto.getPosterUrl() );
        movieApi.stillUrl( movieApiDto.getStillUrl() );
        movieApi.staffs( movieApiDto.getStaffs() );
        movieApi.updatedAt( movieApiDto.getUpdatedAt() );

        return movieApi.build();
    }

    @Override
    public MovieApiDto toDto(MovieApi movieApi) {
        if ( movieApi == null ) {
            return null;
        }

        MovieApiDto.MovieApiDtoBuilder movieApiDto = MovieApiDto.builder();

        movieApiDto.docId( movieApi.getDocId() );
        movieApiDto.title( movieApi.getTitle() );
        movieApiDto.titleEng( movieApi.getTitleEng() );
        movieApiDto.titleOrg( movieApi.getTitleOrg() );
        movieApiDto.director( movieApi.getDirector() );
        movieApiDto.actor( movieApi.getActor() );
        movieApiDto.nation( movieApi.getNation() );
        movieApiDto.company( movieApi.getCompany() );
        movieApiDto.prodYear( movieApi.getProdYear() );
        movieApiDto.plot( movieApi.getPlot() );
        movieApiDto.runtime( movieApi.getRuntime() );
        movieApiDto.rating( movieApi.getRating() );
        movieApiDto.genre( movieApi.getGenre() );
        movieApiDto.repRlsDate( movieApi.getRepRlsDate() );
        movieApiDto.keywords( movieApi.getKeywords() );
        movieApiDto.posterUrl( movieApi.getPosterUrl() );
        movieApiDto.stillUrl( movieApi.getStillUrl() );
        movieApiDto.staffs( movieApi.getStaffs() );
        movieApiDto.updatedAt( movieApi.getUpdatedAt() );

        return movieApiDto.build();
    }

    @Override
    public List<MovieApi> toEntity(List<MovieApiDto> movieApiDtoList) {
        if ( movieApiDtoList == null ) {
            return null;
        }

        List<MovieApi> list = new ArrayList<MovieApi>( movieApiDtoList.size() );
        for ( MovieApiDto movieApiDto : movieApiDtoList ) {
            list.add( toEntity( movieApiDto ) );
        }

        return list;
    }

    @Override
    public List<MovieApiDto> toDto(List<MovieApi> movieApiList) {
        if ( movieApiList == null ) {
            return null;
        }

        List<MovieApiDto> list = new ArrayList<MovieApiDto>( movieApiList.size() );
        for ( MovieApi movieApi : movieApiList ) {
            list.add( toDto( movieApi ) );
        }

        return list;
    }
}
