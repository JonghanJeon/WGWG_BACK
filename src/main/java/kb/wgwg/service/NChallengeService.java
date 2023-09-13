package kb.wgwg.service;


import kb.wgwg.domain.*;
import kb.wgwg.dto.ChallengeDTO.*;
import kb.wgwg.repository.ChallengeRepository;
import kb.wgwg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class NChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public NChallengeInsertResponseDTO insertNChallenge(NChallengeInsertRequestDTO dto) {
        User theUser = userRepository.findById(dto.getOwnerId()).orElseThrow(
                () -> new EntityNotFoundException()
        );

        NChallengeInsertEndDateRequestDTO finalDto = NChallengeInsertEndDateRequestDTO.builder()
                .ownerId(dto.getOwnerId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .startDate(dto.getStartDate())
                .endDate(dto.getStartDate().plusDays(7))
                .deposit(dto.getDeposit())
                .limitAmount(dto.getLimitAmount())
                .account(dto.getAccount())
                .build();

        NChallenge theChallenge = challengeRepository.save(modelMapper.map(finalDto, NChallenge.class));

        ChallengeUser theParticipant = ChallengeUser.builder()
                                                    .isSuccess(true)
                                                    .account(dto.getAccount())
                                                    .build();

        theParticipant.addParticipant(theChallenge);
        theParticipant.setParticipant(theUser);
        entityManager.persist(theParticipant);

        return modelMapper.map(theChallenge, NChallengeInsertResponseDTO.class);
    }

    public void participateNChallenge(NChallengeParticipateRequestDTO dto) {
        User theUser = userRepository.findById(dto.getUserSeq()).orElseThrow(
                EntityNotFoundException::new
        );

        Challenge theChallenge = challengeRepository.findById(dto.getChallengeId()).orElseThrow(
                EntityNotFoundException::new
        );

        theChallenge.getParticipants().stream().forEach(
                participant -> {
                    if (participant.getParticipant().getUserSeq() == theUser.getUserSeq()) {
                        throw new IllegalArgumentException();
                    }
                }
        );

        ChallengeUser theParticipant = ChallengeUser.builder()
                                                    .isSuccess(true)
                                                    .account(dto.getAccount())
                                                    .build();

        theParticipant.addParticipant(theChallenge);
        theParticipant.setParticipant(theUser);
        entityManager.persist(theParticipant);
    }

    public void deleteNChallenge(Long id) {
        challengeRepository.deleteByChallengeId(id);
    }

    @Transactional(readOnly = true)
    public Page<NChallengeListResponseDTO> findNChallengeByStatus(NChallengeListRequestDTO requestDTO, Pageable pageable) {
        Page<Challenge> page;
        if(requestDTO.getStatus().equals("전체보기")) {
            page = challengeRepository.findAll(pageable);
        } else {
            page = challengeRepository.findAllByStatus(requestDTO.getStatus(), pageable);
        }

        // Page<Challenge>을 Page<NChallenge>로 변환
        Page<NChallenge> nChallengePage = page.map(challenge -> (NChallenge) challenge);
        Page<NChallengeListResponseDTO> dtoPage = nChallengePage.map(new Function<NChallenge, NChallengeListResponseDTO>() {
            @Override
            public NChallengeListResponseDTO apply(NChallenge ch) {
                NChallengeListResponseDTO dto = NChallengeListResponseDTO.builder()
                        .challengeId(ch.getChallengeId())
                        .title(ch.getTitle())
                        .status(ch.getStatus())
                        .deposit(ch.getDeposit())
                        .startDate(ch.getStartDate())
                        .endDate(ch.getEndDate())
                        .build();
                return dto;
            }
        });
        return dtoPage;
    }
}
