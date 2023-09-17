package com.talkingPotatoes.potatoesProject.common.batch.job;

import com.talkingPotatoes.potatoesProject.blog.repository.LikesRepository;
import com.talkingPotatoes.potatoesProject.common.batch.tasklet.DeleteLikesTasklet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeleteLikesBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager batchTransactionManager;

    private final LikesRepository likesRepository;

    @Bean
    public Job deleteLikesJob() {
        return new JobBuilder("deleteLikesJob", jobRepository) // 메소드 이름과 동일
                .start(deleteLikesStep()) // 실행할 Step
                .on("FAILED") // 실행한 Step이 실패했을 경우
                .stopAndRestart(deleteLikesStep()) // 멈추거나 해당 Step 재실행
                .on("*") // 실패 외의 경우
                .end() // 해당 Step 종료
                .end() // 모든 작업 종료
                .build();
    }

    @Bean
    public Step deleteLikesStep() {
        return new StepBuilder("deleteLikesStep", jobRepository)
                .tasklet(new DeleteLikesTasklet(likesRepository), batchTransactionManager)
                .build();
    }

}
