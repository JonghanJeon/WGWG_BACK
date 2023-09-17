package kb.wgwg.service;

import kb.wgwg.domain.ChallengeUser;
import kb.wgwg.domain.User;
import kb.wgwg.dto.ChallengeUserDTO;
import kb.wgwg.dto.ChallengeUserDTO.*;
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

    public Page<ReadChallengeUserResponseDTO> readChallengeUserByChallengeId(ReadChallengeUserRequestDTO dto, Pageable pageable){
        Page<ReadChallengeUserResponseDTO> page = challengeUserRep.findAllByChallenge(dto.getChallengeId(), pageable);
        System.out.println(page);
        return page;
    }
}
