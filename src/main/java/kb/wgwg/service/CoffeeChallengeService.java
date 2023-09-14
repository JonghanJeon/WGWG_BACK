package kb.wgwg.service;

import kb.wgwg.domain.Challenge;
import kb.wgwg.dto.ChallengeDTO;
import kb.wgwg.repository.ChallengeRepository;
import kb.wgwg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class CoffeeChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public int updateCoffeeChallenge(ChallengeDTO.CoffeeChallengeUpdateDTO dto) {
        Challenge challenge = challengeRepository.findById(dto.getChallengeId()).orElseThrow(
                () -> new EntityNotFoundException("해당 챌린지를 찾을 수 없습니다.")
        );

        return challengeRepository.updateChallengeByChallengeId(dto.getChallengeId(), dto.getTitle(), dto.getDescription(), dto.getStartDate(), dto.getEndDate(), dto.getSavingAmount());
    }
}
