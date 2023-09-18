package kb.wgwg.service;

import kb.wgwg.domain.Challenge;
import kb.wgwg.domain.ChallengeUser;
import kb.wgwg.domain.User;
import kb.wgwg.dto.ChallengeUserDTO;
import kb.wgwg.dto.ChallengeUserDTO.*;
import kb.wgwg.repository.ChallengeRepository;
import kb.wgwg.repository.ChallengeUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeUserService {

    private final ChallengeUserRepository challengeUserRep;
    private final ChallengeRepository challengeRep;

    public Page<ReadChallengeUserResponseDTO> readChallengeUserByChallengeId(ReadChallengeUserRequestDTO dto, Pageable pageable){
        Page<ReadChallengeUserResponseDTO> page = challengeUserRep.findAllByChallenge(dto.getChallengeId(), pageable);
        System.out.println(page);
        return page;
    }

    public CheckChallengeUserResponseDTO checkChallengeUser(CheckChallengeUserRequestDTO dto){
        Long ownerId = challengeUserRep.findOwnerIdByChallengeId(dto.getChallengeId());
        String challengeType = challengeRep.findChallengeTypeByChallengeId(dto.getChallengeId());
        if(ownerId == dto.getUserSeq()){
            CheckChallengeUserResponseDTO result = CheckChallengeUserResponseDTO.builder()
                    .participantType("개설자")
                    .challengeType(challengeType)
                    .build();
            return result;
        }
        List<Long> list = challengeUserRep.findParticipantIdByChallengeId(dto.getChallengeId());
        for(Long seq : list){
            if(seq == dto.getUserSeq()){
                CheckChallengeUserResponseDTO result = CheckChallengeUserResponseDTO.builder()
                        .participantType("참여자")
                        .challengeType(challengeType)
                        .build();
                return result;
            }
        }
        CheckChallengeUserResponseDTO result = CheckChallengeUserResponseDTO.builder()
                .participantType("비참여자")
                .build();
        return result;
    }
}
