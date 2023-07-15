package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.DirectorDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Director;
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
public class DirectorMapperImpl implements DirectorMapper {

    @Override
    public Director toEntity(DirectorDto directorDto) {
        if ( directorDto == null ) {
            return null;
        }

        Director.DirectorBuilder director = Director.builder();

        director.id( directorDto.getId() );
        director.docId( directorDto.getDocId() );
        director.directorNm( directorDto.getDirectorNm() );
        director.directorEnNm( directorDto.getDirectorEnNm() );
        director.directorId( directorDto.getDirectorId() );

        return director.build();
    }

    @Override
    public DirectorDto toDto(Director director) {
        if ( director == null ) {
            return null;
        }

        DirectorDto.DirectorDtoBuilder directorDto = DirectorDto.builder();

        directorDto.id( director.getId() );
        directorDto.docId( director.getDocId() );
        directorDto.directorNm( director.getDirectorNm() );
        directorDto.directorEnNm( director.getDirectorEnNm() );
        directorDto.directorId( director.getDirectorId() );

        return directorDto.build();
    }

    @Override
    public List<Director> toEntity(List<DirectorDto> directorDtoList) {
        if ( directorDtoList == null ) {
            return null;
        }

        List<Director> list = new ArrayList<Director>( directorDtoList.size() );
        for ( DirectorDto directorDto : directorDtoList ) {
            list.add( toEntity( directorDto ) );
        }

        return list;
    }

    @Override
    public List<DirectorDto> toDto(List<Director> directorList) {
        if ( directorList == null ) {
            return null;
        }

        List<DirectorDto> list = new ArrayList<DirectorDto>( directorList.size() );
        for ( Director director : directorList ) {
            list.add( toDto( director ) );
        }

        return list;
    }
}
