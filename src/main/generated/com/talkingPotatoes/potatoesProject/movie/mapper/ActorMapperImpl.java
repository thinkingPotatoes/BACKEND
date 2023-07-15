package com.talkingPotatoes.potatoesProject.movie.mapper;

import com.talkingPotatoes.potatoesProject.movie.dto.ActorDto;
import com.talkingPotatoes.potatoesProject.movie.entity.Actor;
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
public class ActorMapperImpl implements ActorMapper {

    @Override
    public Actor toEntity(ActorDto actorDto) {
        if ( actorDto == null ) {
            return null;
        }

        Actor.ActorBuilder actor = Actor.builder();

        actor.id( actorDto.getId() );
        actor.docId( actorDto.getDocId() );
        actor.actorNm( actorDto.getActorNm() );
        actor.actorEnNm( actorDto.getActorEnNm() );
        actor.actorId( actorDto.getActorId() );

        return actor.build();
    }

    @Override
    public ActorDto toDto(Actor actor) {
        if ( actor == null ) {
            return null;
        }

        ActorDto.ActorDtoBuilder actorDto = ActorDto.builder();

        actorDto.id( actor.getId() );
        actorDto.docId( actor.getDocId() );
        actorDto.actorNm( actor.getActorNm() );
        actorDto.actorEnNm( actor.getActorEnNm() );
        actorDto.actorId( actor.getActorId() );

        return actorDto.build();
    }

    @Override
    public List<Actor> toEntity(List<ActorDto> actorDtoList) {
        if ( actorDtoList == null ) {
            return null;
        }

        List<Actor> list = new ArrayList<Actor>( actorDtoList.size() );
        for ( ActorDto actorDto : actorDtoList ) {
            list.add( toEntity( actorDto ) );
        }

        return list;
    }

    @Override
    public List<ActorDto> toDto(List<Actor> actorList) {
        if ( actorList == null ) {
            return null;
        }

        List<ActorDto> list = new ArrayList<ActorDto>( actorList.size() );
        for ( Actor actor : actorList ) {
            list.add( toDto( actor ) );
        }

        return list;
    }
}
