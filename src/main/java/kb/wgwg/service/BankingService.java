package kb.wgwg.service;

import kb.wgwg.domain.Banking;
import kb.wgwg.domain.User;
import kb.wgwg.dto.BankingDTO.*;
import kb.wgwg.repository.BankingRepository;
import kb.wgwg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class BankingService {

    private final ModelMapper modelMapper;
    private final BankingRepository bankingRepository;
    private final UserRepository userRepository;

    public BankingUpdateDTO updateBanking(BankingUpdateDTO dto) {
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

    public Page<BankingListResponseDTO> findBankingByYearAndMonth(int year, int month, Pageable pageable) {
        Page<Banking> page = bankingRepository.findMonth(year, month, pageable);
        System.out.println(page);

        Page<BankingListResponseDTO> dtoPage = page.map(new Function<Banking, BankingListResponseDTO>() {
            @Override
            public BankingListResponseDTO apply(Banking banking) {
                System.out.println("****************");
                System.out.println(banking.getBankingDate());

                BankingListResponseDTO dto = BankingListResponseDTO.builder()
                        .bankingId(banking.getBankingId())
                        .bankingDate(banking.getBankingDate())
                        .type(banking.getType())
                        .category(banking.getCategory())
                        .amount(banking.getAmount())
                        .content(banking.getContent())
                        .build();

                System.out.println(dto);

                return dto;
            }
        });

        return dtoPage;
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
