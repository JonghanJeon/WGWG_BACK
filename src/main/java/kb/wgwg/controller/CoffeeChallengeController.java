package kb.wgwg.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.dto.ChallengeDTO.*;
import kb.wgwg.service.CoffeeChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

import static kb.wgwg.common.ResponseMessage.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/challenges")
public class CoffeeChallengeController {

    private final CoffeeChallengeService coffeeChallengeService;

    @PostMapping(value = "/update/coffee")
    public ResponseEntity<BaseResponseDTO> updateCoffeeChallenge(@RequestBody CoffeeChallengeUpdateDTO dto) {
        BaseResponseDTO response = new BaseResponseDTO();
        try {
            int updateRows = coffeeChallengeService.updateCoffeeChallenge(dto);
            response.setStatus(StatusCode.OK);
            response.setMessage(ResponseMessage.CHALLENGE_UPDATE_SUCCESS);
            response.setSuccess(true);
            response.setData(updateRows);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e){
            response.setStatus(StatusCode.BAD_REQUEST);
            response.setMessage(ResponseMessage.NOT_FOUND_CHALLENGE);
            response.setSuccess(false);
            return ResponseEntity.badRequest().body(response);
        } catch (Exception e){
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            response.setSuccess(false);
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping(value = "/insert/coffee")
    public ResponseEntity<BaseResponseDTO> createChallenge(@RequestBody CoffeeChallengeInsertRequestDTO dto) {
        BaseResponseDTO<CoffeeChallengeInsertResponseDTO> response = new BaseResponseDTO<>();

        try {
            CoffeeChallengeInsertResponseDTO result = coffeeChallengeService.insertCoffeeChallenge(dto);
            response.setMessage("성공적으로 챌린지가 생성되었습니다.");
            response.setStatus(200);
            response.setSuccess(true);
            response.setData(result);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(404);
            response.setSuccess(false);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(500);
            response.setSuccess(false);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping(value = "/participate/coffee")
    public ResponseEntity<BaseResponseDTO> participateNChallenge(@RequestBody ChallengeParticipateRequestDTO dto) {
        BaseResponseDTO<Void> response = new BaseResponseDTO<>();
        try {
            coffeeChallengeService.participateCoffeeChallenge(dto);
            response.setMessage("성공적으로 챌린지에 참여하었습니다.");
            response.setStatus(200);
            response.setSuccess(true);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(404);
            response.setSuccess(false);

            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            response.setMessage("이미 참여중인 챌린지입니다.");
            response.setStatus(400);
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);
        } catch (IndexOutOfBoundsException e) {
            response.setMessage("참여자가 가득 차 더 이상 참여가 불가능합니다.");
            response.setStatus(400);
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);
        } catch (Exception e) {
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(500);
            response.setSuccess(false);

            return ResponseEntity.internalServerError().body(response);
        }
    }
}
