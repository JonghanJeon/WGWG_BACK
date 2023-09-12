package kb.wgwg.service;


import kb.wgwg.domain.Challenge;
import kb.wgwg.domain.ChallengeUser;
import kb.wgwg.domain.NChallenge;
import kb.wgwg.domain.User;
import kb.wgwg.dto.ChallengeDTO.*;
import kb.wgwg.repository.ChallengeRepository;
import kb.wgwg.repository.ChallengeUserRepository;
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
public class NChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public NChallengeInsertResponseDTO insertNChallenge(NChallengeInsertRequestDTO dto) {
        User theUser = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException()
        );

        NChallenge theChallenge = challengeRepository.save(modelMapper.map(dto, NChallenge.class));

        ChallengeUser theParticipant = ChallengeUser.builder()
                                                    .account(dto.getAccount())
                                                    .build();

        theParticipant.addParticipant(theChallenge);
        theParticipant.setParticipant(theUser);
        entityManager.persist(theParticipant);

        return modelMapper.map(theChallenge, NChallengeInsertResponseDTO.class);
    }

    public void participateNChallenge(NChallengeParticipateRequestDTO dto) {
        User theUser = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException()
        );

        Challenge theChallenge = challengeRepository.findById(dto.getChallengeId()).orElseThrow(
                () -> new EntityNotFoundException()
        );

        theChallenge.getParticipants().stream().forEach(
                participant -> {
                    if (participant.getParticipant().getUserSeq() == theUser.getUserSeq()) {
                        throw new IllegalArgumentException();
                    }
                }
        );

        ChallengeUser theParticipant = ChallengeUser.builder()
                                                    .account(dto.getAccount())
                                                    .build();

        theParticipant.addParticipant(theChallenge);
        theParticipant.setParticipant(theUser);
        entityManager.persist(theParticipant);
    }
}
