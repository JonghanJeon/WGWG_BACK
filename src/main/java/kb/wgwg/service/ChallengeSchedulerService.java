package kb.wgwg.service;

import kb.wgwg.repository.ChallengeRepository;
import kb.wgwg.repository.ChallengeUserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class ChallengeSchedulerService {
    private final ModelMapper modelMapper;
    private final ChallengeRepository challengeRepository;
    private final ChallengeUserRepository challengeUserRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void updateChallengeStatus(){
        //진행 -> 종료
        challengeRepository.updateChallengeStateToFinish();

        //모집 -> 진행
        challengeRepository.updateChallengeStateToOngoing();
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateChallengeUserStatusOfSuccess(){
        challengeUserRepository.updateChallengeUserStateOfSuccess();
    }
}
