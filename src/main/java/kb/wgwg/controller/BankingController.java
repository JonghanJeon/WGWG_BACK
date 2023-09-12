package kb.wgwg.controller;

import kb.wgwg.dto.BankingDTO.*;
import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.service.BankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import static kb.wgwg.common.ResponseMessage.INTERNAL_SERVER_ERROR;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/banking")
public class BankingController {
    private final BankingService bankingService;

    @PostMapping(value = "/read")
    public ResponseEntity<BaseResponseDTO> readBankingsByBankingDate(@RequestBody BankingListRequestDTO dto, @PageableDefault(size = 10) Pageable pageable) {
        BaseResponseDTO<Page<BankingListResponseDTO>> response = new BaseResponseDTO<>();
        System.out.println(dto.getBankingDate());
        try {
            Page<BankingListResponseDTO> result = bankingService.findBankingByYearAndMonth(dto.getBankingDate().getYear(), dto.getBankingDate().getMonthValue(), pageable);
            response.setMessage("성공적으로 입출금 내역을 불러왔습니다.");
            response.setStatus(200);
            response.setSuccess(true);
            response.setData(result);
        } catch(EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(400);
            response.setSuccess(false);
        } catch(Exception e) {
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(500);
            response.setSuccess(false);
        }
        return ResponseEntity.ok(response);
    }
}
