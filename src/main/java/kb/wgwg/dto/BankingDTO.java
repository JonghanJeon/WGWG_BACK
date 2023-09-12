package kb.wgwg.dto;


import kb.wgwg.domain.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.time.LocalDateTime;

public class BankingDTO {
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class BankingUpdateDTO{
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
}
