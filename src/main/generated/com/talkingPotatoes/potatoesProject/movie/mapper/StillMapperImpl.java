package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.StillDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Still;
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
public class StillMapperImpl implements StillMapper {

    @Override
    public Still toEntity(StillDto stillDto) {
        if ( stillDto == null ) {
            return null;
        }

        Still.StillBuilder still = Still.builder();

        still.id( stillDto.getId() );
        still.docId( stillDto.getDocId() );
        still.stillUrl( stillDto.getStillUrl() );

        return still.build();
    }

    @Override
    public StillDto toDto(Still still) {
        if ( still == null ) {
            return null;
        }

        StillDto.StillDtoBuilder stillDto = StillDto.builder();

        stillDto.id( still.getId() );
        stillDto.docId( still.getDocId() );
        stillDto.stillUrl( still.getStillUrl() );

        return stillDto.build();
    }

    @Override
    public List<Still> toEntity(List<StillDto> stillDtoList) {
        if ( stillDtoList == null ) {
            return null;
        }

        List<Still> list = new ArrayList<Still>( stillDtoList.size() );
        for ( StillDto stillDto : stillDtoList ) {
            list.add( toEntity( stillDto ) );
        }

        return list;
    }

    @Override
    public List<StillDto> toDto(List<Still> stillList) {
        if ( stillList == null ) {
            return null;
        }

        List<StillDto> list = new ArrayList<StillDto>( stillList.size() );
        for ( Still still : stillList ) {
            list.add( toDto( still ) );
        }

        return list;
    }
}
