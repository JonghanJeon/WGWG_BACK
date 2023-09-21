package kb.wgwg.service;

import kb.wgwg.domain.*;
import kb.wgwg.dto.BankingDTO.*;
import kb.wgwg.repository.BankingRepository;
import kb.wgwg.repository.ChallengeRepository;
import kb.wgwg.repository.ChallengeUserRepository;
import kb.wgwg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BankingService {

    private final ModelMapper modelMapper;
    private final BankingRepository bankingRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    public BankingUpdateDTO updateBanking(BankingUpdateDTO dto) {
        Banking banking = bankingRepository.findById(dto.getBankingId()).orElseThrow(
                () -> new EntityNotFoundException("해당 입출금 내역을 찾을 수 없습니다.")
        );

        banking.updateAmount(dto.getAmount());
        banking.updateBankingDate(dto.getBankingDate());
        banking.updateType(dto.getType());
        banking.updateCategory(dto.getCategory());
        banking.updateContent(dto.getContent());


        BankingUpdateDTO result = modelMapper.map(banking, BankingUpdateDTO.class);

        return result;
    }

    @Transactional(readOnly = true)
    public BankingReadResponseDTO findBankingById(Long id) {
        Banking banking = bankingRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 가계부 내역을 찾을 수 없습니다.")
        );

        return BankingReadResponseDTO.builder()
                .bankingId(banking.getBankingId())
                .type(banking.getType())
                .bankingDate(banking.getBankingDate())
                .amount(banking.getAmount())
                .category(banking.getCategory())
                .content(banking.getContent())
                .build();
    }


    public Page<BankingListResponseDTO> findBankingByYearAndMonth(int year, int month, Long userSeq,Pageable pageable) {
        Page<Banking> page = bankingRepository.findMonth(year, month, userSeq, pageable);

        Page<BankingListResponseDTO> dtoPage = page.map(new Function<Banking, BankingListResponseDTO>() {
            @Override
            public BankingListResponseDTO apply(Banking banking) {
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
    public List<ReadCategoryResponseDTO> readCategoryProportion(ReadCategoryRequestDTO dto) {
        User user = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException("사용자를 찾을 수 없습니다.")
        );
        List<Object[]> list = bankingRepository.readCategoryProportion(dto.getUserSeq(), dto.getCheckMonth()+"-01");

        List<ReadCategoryResponseDTO> result = new ArrayList<>();
        for(Object[] li : list){
            ReadCategoryResponseDTO responseDTO = new ReadCategoryResponseDTO();
            responseDTO.setCategory((String) li[0]);
            responseDTO.setTotal(((BigDecimal) li[1]).intValue());
            result.add(responseDTO);
        }
        return result;
    }

    public ReadTotalResponseDTO calculateTotalSpend(ReadTotalRequestDTO dto) {
        User user = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException("사용자를 찾을 수 없습니다.")
        );

        YearMonth yearMonth = YearMonth.from(dto.getCheckMonth()); // 해당 월을 추출
        String startDate = String.valueOf(yearMonth.atDay(1).atStartOfDay().toLocalDate()); // 해당 월의 시작일 (1일)
        String endDate = String.valueOf(yearMonth.atEndOfMonth());

        // 데이터베이스에서 해당 월의 소비 내역을 조회
        List<Banking> bankingList = bankingRepository.findByBankingDateBetweenAndUserSeq(startDate, endDate, dto.getUserSeq());
        System.out.println(bankingList);

        // 수입과 지출을 각각 계산
        int totalIncome = 0;
        int totalExpense = 0;

        for (Banking banking : bankingList) {
            if ("수입".equals(banking.getType())) {
                totalIncome += banking.getAmount();
            } else if ("지출".equals(banking.getType())) {
                totalExpense += banking.getAmount();
            }
        }
        ReadTotalResponseDTO responseDTO = new ReadTotalResponseDTO();
        responseDTO.setTotalIncome(totalIncome);
        responseDTO.setTotalExpense(totalExpense);
        return responseDTO;
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
                .challengeId(dto.getChallengeId())
                .build();

        Banking saved = bankingRepository.save(banking);
        return saved.getBankingId();
    }

    public int calculateReward(GetTotalRewardRequestDTO dto) {
        List<String> types = Arrays.asList("보증금", "적금액");
        User theUser = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException()
        );

        List<Banking> outputBankingList = bankingRepository.
                findAllByOwnerAndCategoryAndTypeIn(theUser, "챌린지", types);

        int outputAmountInCompleteChallenge = outputBankingList.stream().filter(
                banking -> {
                    if (banking.getChallengeId() == null) return false;
                    Challenge theChallenge = challengeRepository.findById(banking.getChallengeId()).orElseThrow(
                            () -> new EntityNotFoundException()
                    );

                    return theChallenge.getStatus().equals("완료");
                }
        ).mapToInt(Banking::getAmount).sum();

        if (outputAmountInCompleteChallenge != 0) {
            List<Banking> inputBankingList = bankingRepository.findAllByOwnerAndTypeAndCategory(theUser, "입금", "챌린지");
            int totalAmount = inputBankingList.stream().mapToInt(Banking::getAmount).sum();

            return totalAmount - outputAmountInCompleteChallenge;
        }

        return outputAmountInCompleteChallenge;
    }
}
