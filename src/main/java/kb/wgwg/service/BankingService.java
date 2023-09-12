package kb.wgwg.service;

import kb.wgwg.domain.Banking;
import kb.wgwg.domain.User;
import kb.wgwg.dto.UserDTO.*;
import kb.wgwg.repository.BankingRepository;
import kb.wgwg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kb.wgwg.dto.BankingDTO.*;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BankingService {

    private final ModelMapper modelMapper;
    private final BankingRepository bankingRepository;
    private final UserRepository userRepository;

    public BankingUpdateDTO updateBanking(BankingUpdateDTO dto){
        Banking banking = bankingRepository.findById(dto.getBankingId()).orElseThrow(
                () -> new EntityNotFoundException("해당 입출금 내역을 찾을 수 없습니다.")
        );

        banking.updateAmount(dto.getAmount());
        banking.updateBankingDate(dto.getBankingDate());
        banking.updateType(dto.getType());
        banking.updateCategory(dto.getCategory());

        BankingUpdateDTO result = modelMapper.map(banking, BankingUpdateDTO.class);

        return result;
    }

    public void deleteBankingHistory(Long bankingId) {
        try {
            bankingRepository.deleteById(bankingId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Long insertBankingHistory(BankingInsertRequestDTO dto){
        User user = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException("사용자를 찾을 수 없습니다.")
        );

        LocalDateTime insertDate = LocalDateTime.parse(dto.getBankingDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        Banking banking = Banking.builder()
                .owner(user)
                .type(dto.getType())
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .bankingDate(insertDate)
                .content(dto.getContent())
                .build();

        Banking saved = bankingRepository.save(banking);
        return saved.getBankingId();
    }
}
