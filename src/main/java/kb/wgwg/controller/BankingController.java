package kb.wgwg.controller;

import kb.wgwg.dto.BankingDTO.*;
import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.service.BankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import static kb.wgwg.common.ResponseMessage.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/banking")
public class BankingController {
    private final BankingService bankingService;

    @PostMapping(value = "/update")
    public ResponseEntity<BaseResponseDTO> update(@RequestBody BankingUpdateDTO dto){
        BaseResponseDTO<BankingUpdateDTO> response = new BaseResponseDTO<>();
        try {
            BankingUpdateDTO result = bankingService.updateBanking(dto);
            response.setSuccess(true);
            response.setStatus(200);
            response.setData(result);
            response.setMessage("입출금 내역이 성공적으로 수정되었습니다.");
        }catch (EntityNotFoundException e){
            response.setMessage(e.getMessage());
            response.setStatus(404);
            response.setSuccess(false);
        }catch (Exception e){
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(500);
            response.setSuccess(false);
        }

        return ResponseEntity.ok(response);
    }

}
