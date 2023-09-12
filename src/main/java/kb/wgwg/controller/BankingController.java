package kb.wgwg.controller;

import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.service.BankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/banking")
public class BankingController {
    private final BankingService bankingService;

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

}
