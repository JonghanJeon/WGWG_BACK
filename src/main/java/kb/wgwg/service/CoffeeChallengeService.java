package kb.wgwg.service;

import kb.wgwg.domain.*;
import kb.wgwg.dto.ChallengeDTO.*;
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

    private static final int LIMIT_CHALLENGE_USER_SIZE = 30;

    public int updateCoffeeChallenge(CoffeeChallengeUpdateDTO dto) {
        Challenge challenge = challengeRepository.findById(dto.getChallengeId()).orElseThrow(
                () -> new EntityNotFoundException("해당 챌린지를 찾을 수 없습니다.")
        );

        return challengeRepository.updateChallengeByChallengeId(dto.getChallengeId(), dto.getTitle(), dto.getDescription(), dto.getStartDate(), dto.getEndDate(), dto.getSavingAmount());
    }

    public CoffeeChallengeInsertResponseDTO insertCoffeeChallenge(CoffeeChallengeInsertRequestDTO dto) {
        User theUser = userRepository.findById(dto.getOwnerId()).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.")
        );

        CoffeeChallenge theChallenge = challengeRepository.save(modelMapper.map(dto, CoffeeChallenge.class));

        ChallengeUser theParticipant = ChallengeUser.builder()
                .isSuccess(true)
                .account(dto.getAccount())
                .challengeType(dto.getChallengeType())
                .build();

        theParticipant.addParticipant(theChallenge);
        theParticipant.setParticipant(theUser);
        entityManager.persist(theParticipant);

        return modelMapper.map(theChallenge, CoffeeChallengeInsertResponseDTO.class);
    }

    public void participateCoffeeChallenge(ChallengeParticipateRequestDTO dto) {
        User theUser = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.")
        );

        Challenge theChallenge = challengeRepository.findById(dto.getChallengeId()).orElseThrow(
                () -> new EntityNotFoundException("해당 챌린지를 찾을 수 없습니다.")
        );

        theChallenge.getParticipants().stream().forEach(
                participant -> {
                    if (participant.getParticipant().getUserSeq() == theUser.getUserSeq()) {
                        throw new IllegalArgumentException();
                    }
                }
        );

        if (theChallenge.getParticipants().size() == LIMIT_CHALLENGE_USER_SIZE) {
            throw new ArrayIndexOutOfBoundsException();
        }

        ChallengeUser theParticipant = ChallengeUser.builder()
                .isSuccess(true)
                .account(dto.getAccount())
                .challengeType(dto.getChallengeType())
                .build();

        theParticipant.addParticipant(theChallenge);
        theParticipant.setParticipant(theUser);
        entityManager.persist(theParticipant);
    }
}
