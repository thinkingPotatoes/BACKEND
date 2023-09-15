package com.talkingPotatoes.potatoesProject.common.batch.scheduler;

import com.talkingPotatoes.potatoesProject.common.batch.job.DeleteLikesBatch;
import com.talkingPotatoes.potatoesProject.common.batch.job.DeleteUserBatch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class LikesScheduler {
    private final JobLauncher jobLauncher;
    private final DeleteLikesBatch job;

    @Scheduled(cron = "27 * * * * ?")  // 매일 12시마다
    public void runJob() throws Exception {

        try {
            jobLauncher.run(
                    job.deleteLikesJob(), new JobParametersBuilder().addString("dateTime"
                            , LocalDateTime.now().toString()).toJobParameters()
            );
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException | JobRestartException e) {
            log.error(e.getMessage());
        }
    }
}