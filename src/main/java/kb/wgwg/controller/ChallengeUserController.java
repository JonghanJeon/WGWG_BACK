package kb.wgwg.controller;

import kb.wgwg.common.StatusCode;
import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.dto.ChallengeDTO;
import kb.wgwg.dto.ChallengeUserDTO.*;
import kb.wgwg.service.ChallengeUserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challengeuser")
public class ChallengeUserController {

    private final ChallengeUserService challengeUserService;

    @PostMapping("/read")
    public ResponseEntity<BaseResponseDTO> readChallengeUserByChallenge(@RequestBody ReadChallengeUserRequestDTO dto, @PageableDefault(size = 15) Pageable pageable){
        BaseResponseDTO<Page<ReadChallengeUserResponseDTO>> response = new BaseResponseDTO<>();
        try{
            Page<ReadChallengeUserResponseDTO> result = challengeUserService.readChallengeUserByChallengeId(dto, pageable);
            response.setMessage("참가자 정보 읽기 완료.");
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/checkuser")
    public ResponseEntity<BaseResponseDTO> checkChallengeUser(@RequestBody CheckChallengeUserRequestDTO dto){
        BaseResponseDTO<CheckChallengeUserResponseDTO> response = new BaseResponseDTO<>();
        try {
            CheckChallengeUserResponseDTO result = challengeUserService.checkChallengeUser(dto);
            response.setMessage("유저 식별 완료.");
            response.setStatus(StatusCode.OK);
            response.setSuccess(true);
            response.setData(result);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
            return ResponseEntity.internalServerError().body(response);
        }

    }
}
