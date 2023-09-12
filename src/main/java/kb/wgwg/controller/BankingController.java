package kb.wgwg.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.dto.BankingDTO.*;
import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.service.BankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import java.util.HashMap;
import java.util.Map;

import static kb.wgwg.common.ResponseMessage.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/banking")
public class BankingController {
    private final BankingService bankingService;

    @PostMapping(value = "/update")
    public ResponseEntity<BaseResponseDTO> updateBankingHistory(@RequestBody BankingUpdateDTO dto){
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
    
    @DeleteMapping(value = "/delete/{bankingId}")
    public ResponseEntity<BaseResponseDTO> deleteBankingHistory(@PathVariable(value = "bankingId") Long bankingId) {
        BaseResponseDTO<Void> response = new BaseResponseDTO<>();

        try {
            bankingService.deleteBankingHistory(bankingId);
            response.setMessage("입출금 내역이 성공적으로 삭제되었습니다.");
            response.setSuccess(true);
            response.setStatus(200);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            response.setStatus(500);

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/insert")
    public ResponseEntity bankingInsert(@RequestBody BankingInsertRequestDTO dto){
        BaseResponseDTO response = new BaseResponseDTO<>();

        try {
            Long savedId = bankingService.insertBankingHistory(dto);
            Map<String, Long> map = new HashMap<>();
            map.put("bankingSeq", savedId);

            response.setStatus(StatusCode.OK);
            response.setMessage(ResponseMessage.BANKING_INSERT_SUCCESS);
            response.setSuccess(true);
            response.setData(map);

            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e){
            response.setStatus(StatusCode.NOT_FOUND);
            response.setMessage(ResponseMessage.NOT_FOUND_USER);
            response.setSuccess(false);

            return ResponseEntity.badRequest().body(response);
        } catch (RuntimeException e){
            e.printStackTrace();
            response.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            response.setMessage(e.getMessage());
            response.setSuccess(false);

            return ResponseEntity.internalServerError().body(response);
        }
    }
}
