package com.talkingPotatoes.potatoesProject.common.batch.tasklet;

import com.talkingPotatoes.potatoesProject.user.entity.User;
import com.talkingPotatoes.potatoesProject.user.repository.UserRepository;
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
public class DeleteUserTasklet implements Tasklet {

    private final UserRepository userRepository;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        List<User> users = this.userRepository
                        .findByEmailCheckedIsFalseAndUpdatedAtIsLessThan(
                                LocalDateTime.now().minusDays(7));

        log.info("삭제 회원은 " + users.size() + " 명 입니다.");
        userRepository.deleteAll(users);

        return RepeatStatus.FINISHED;
    }
}
