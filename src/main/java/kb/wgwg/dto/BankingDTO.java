package kb.wgwg.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

public class BankingDTO {
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class BankingUpdateDTO {
        // 고유 식별 번호
        private Long bankingId;

        // 금액
        private int amount;

        // 입금/출금/보증금/적금
        private String type;

        // 입출금 일자
        private LocalDateTime bankingDate;

        // 카테고리
        private String category;


    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static final class BankingInsertRequestDTO {
        private Long userSeq;
        private String type;
        private int amount;
        private String bankingDate;
        private String category;
        private String content;
    }
}