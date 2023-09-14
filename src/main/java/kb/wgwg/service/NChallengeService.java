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
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class NChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    private static final int LIMIT_CHALLENGE_USER_SIZE = 30;

    public NChallengeInsertResponseDTO insertNChallenge(NChallengeInsertRequestDTO dto) {
        User theUser = userRepository.findById(dto.getOwnerId()).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.")
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
                .challengeType(dto.getChallengeType())
                .build();

        NChallenge theChallenge = challengeRepository.save(modelMapper.map(finalDto, NChallenge.class));

        ChallengeUser theParticipant = ChallengeUser.builder()
                                                    .isSuccess(true)
                                                    .account(dto.getAccount())
                                                    .challengeType(dto.getChallengeType())
                                                    .build();

        theParticipant.addParticipant(theChallenge);
        theParticipant.setParticipant(theUser);
        entityManager.persist(theParticipant);

        return modelMapper.map(theChallenge, NChallengeInsertResponseDTO.class);
    }

    public void participateNChallenge(ChallengeParticipateRequestDTO dto) {
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

    public int updateNChallenge(NChallengeUpdateDTO dto) {
        Challenge challenge = challengeRepository.findById(dto.getChallengeId()).orElseThrow(
                () -> new EntityNotFoundException("해당 챌린지를 찾을 수 없습니다.")
        );

        return challengeRepository.updateChallengeByChallengeId(dto.getChallengeId(), dto.getTitle(), dto.getDescription(), dto.getStartDate(), dto.getLimitAmount());
    }

    public void deleteNChallenge(Long id) {
        challengeRepository.deleteByChallengeId(id);
    }

    @Transactional(readOnly = true)
    public Page<NChallengeListResponseDTO> findNChallengeByStatus(ChallengeListRequestDTO requestDTO, Pageable pageable) {
        Page<Challenge> page;

        if(requestDTO.getStatus().equals("전체보기")) {
            page = challengeRepository.findAllByChallengeType(requestDTO.getChallengeType(), pageable);
        } else {
            page = challengeRepository.findAllByStatusAndChallengeType(requestDTO.getStatus(), requestDTO.getChallengeType(), pageable);
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

    @Transactional(readOnly = true)
    public NChallengeReadResponseDTO findNChallengeById(Long id) {
        NChallenge nchallenge = (NChallenge) challengeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 챌린지를 찾을 수 없습니다.")
        );

        Map<String, Boolean> isSuccessList = new HashMap<>();
        for (ChallengeUser participant : nchallenge.getParticipants()) {
            isSuccessList.put(
                    participant.getParticipant().getNickName(),
                    participant.isSuccess()
                    );
        }
        System.out.println("isSuccessList = " + isSuccessList);
        return NChallengeReadResponseDTO.builder()
                .title(nchallenge.getTitle())
                .description(nchallenge.getDescription())
                .startDate(nchallenge.getStartDate())
                .endDate(nchallenge.getEndDate())
                .deposit(nchallenge.getDeposit())
                .limitAmount(nchallenge.getLimitAmount())
                .challengeType(nchallenge.getChallengeType())
                .status(nchallenge.getStatus())
                .isSuccessList(isSuccessList)
                .build();
    }
}