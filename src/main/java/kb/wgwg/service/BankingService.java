package kb.wgwg.service;

import kb.wgwg.domain.Banking;
import kb.wgwg.domain.User;
import kb.wgwg.dto.BankingDTO.*;
import kb.wgwg.dto.UserDTO.*;
import kb.wgwg.repository.BankingRepository;
import kb.wgwg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.function.Function;

@Service
@Transactional
@RequiredArgsConstructor
public class BankingService {

    private final ModelMapper modelMapper;
    private final BankingRepository bankingRepository;

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

}
