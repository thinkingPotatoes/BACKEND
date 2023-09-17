package com.talkingPotatoes.potatoesProject.common.batch.tasklet;

import com.talkingPotatoes.potatoesProject.blog.entity.Likes;
import com.talkingPotatoes.potatoesProject.blog.repository.LikesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DeleteLikesTasklet implements Tasklet {

    private final LikesRepository likesRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<Likes> likes = this.likesRepository
                .findByClickedAndUpdatedAtIsLessThan(
                        false,
                        LocalDateTime.now().minusDays(1));

        likesRepository.deleteAll(likes);

        return RepeatStatus.FINISHED;
    }
}
