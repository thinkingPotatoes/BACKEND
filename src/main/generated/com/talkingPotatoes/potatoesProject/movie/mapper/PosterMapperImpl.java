package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.PosterDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Poster;
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
public class PosterMapperImpl implements PosterMapper {

    @Override
    public Poster toEntity(PosterDto posterDto) {
        if ( posterDto == null ) {
            return null;
        }

        Poster.PosterBuilder poster = Poster.builder();

        poster.id( posterDto.getId() );
        poster.docId( posterDto.getDocId() );
        poster.posterUrl( posterDto.getPosterUrl() );

        return poster.build();
    }

    @Override
    public PosterDto toDto(Poster poster) {
        if ( poster == null ) {
            return null;
        }

        PosterDto.PosterDtoBuilder posterDto = PosterDto.builder();

        posterDto.id( poster.getId() );
        posterDto.docId( poster.getDocId() );
        posterDto.posterUrl( poster.getPosterUrl() );

        return posterDto.build();
    }

    @Override
    public List<Poster> toEntity(List<PosterDto> posterDtoList) {
        if ( posterDtoList == null ) {
            return null;
        }

        List<Poster> list = new ArrayList<Poster>( posterDtoList.size() );
        for ( PosterDto posterDto : posterDtoList ) {
            list.add( toEntity( posterDto ) );
        }

        return list;
    }

    @Override
    public List<PosterDto> toDto(List<Poster> posterList) {
        if ( posterList == null ) {
            return null;
        }

        List<PosterDto> list = new ArrayList<PosterDto>( posterList.size() );
        for ( Poster poster : posterList ) {
            list.add( toDto( poster ) );
        }

        return list;
    }
}
