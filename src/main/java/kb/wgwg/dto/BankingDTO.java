package kb.wgwg.dto;


import lombok.*;

import java.time.LocalDateTime;

public class BankingDTO {
    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class BankingListRequestDTO { //월별로 요청
        private LocalDateTime bankingDate;
    }

    @Getter
    @ToString
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class BankingListResponseDTO {
        private Long bankingId;
        private LocalDateTime bankingDate;
        private String type;
        private String category;
        private int amount;
        private String content;
    }
}
