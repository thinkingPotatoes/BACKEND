package com.talkingPotatoes.potatoesProject.common.batch.job;

import com.talkingPotatoes.potatoesProject.common.batch.tasklet.DeleteUserTasklet;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeleteUserBatch {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager batchTransactionManager;

    private final UserRepository userRepository;

    @Bean
    public Job deleteUserJob() {
        return new JobBuilder("deleteUserJob", jobRepository) // 메소드 이름과 동일
                .start(deleteUserStep()) // 실행할 Step
                .on("FAILED") // 실행한 Step이 실패했을 경우
                .stopAndRestart(deleteUserStep()) // 멈추거나 해당 Step 재실행
                .on("*") // 실패 외의 경우
                .end() // 해당 Step 종료
                .end() // 모든 작업 종료
                .build();
    }

    @Bean
    public Step deleteUserStep() {
        return new StepBuilder("deleteUserStep", jobRepository)
                .tasklet(new DeleteUserTasklet(userRepository), batchTransactionManager)
                .build();
    }

//    private ListItemReader<User> userReader() {
//        return new ListItemReader<>(
//                this.userRepository
//                        .findByEmailCheckedIsFalseAndUpdatedAtIsGreaterThan(
//                                LocalDateTime.now().plusDays(7))
//        );
//    }

    @Bean
    public ItemWriter<Object> userWriter() {
        return this::loggingAll;
    }

    private void loggingAll(final Chunk<?> objects) {
        objects.getItems().forEach(item -> log.info(item.toString()));
    }
}