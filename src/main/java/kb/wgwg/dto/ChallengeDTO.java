package kb.wgwg.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class ChallengeDTO {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class NChallengeInsertRequestDTO {

        private Long ownerId;

        private String title;

        private String description;

        private String status;

        private LocalDateTime startDate;

        private int deposit;

        private int limitAmount;

        private String account;

        private String challengeType;

        @Builder
        public NChallengeInsertRequestDTO(String title, String description, String status,
                                          LocalDateTime startDate, int deposit,
                                          int limitAmount, String account, String challengeType)
        {
            this.title = title;
            this.description = description;
            this.status = status;
            this.startDate = startDate;
            this.deposit = deposit;
            this.limitAmount = limitAmount;
            this.account = account;
            this.challengeType = challengeType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class NChallengeInsertEndDateRequestDTO {

        private Long ownerId;

        private String title;

        private String description;

        private String status;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        private int deposit;

        private int limitAmount;

        private String account;

        private String challengeType;

        @Builder
        public NChallengeInsertEndDateRequestDTO(Long ownerId, String title, String description, String status,
                                          LocalDateTime startDate, LocalDateTime endDate, int deposit,
                                          int limitAmount, String account, String challengeType)
        {
            this.ownerId = ownerId;
            this.title = title;
            this.description = description;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
            this.deposit = deposit;
            this.limitAmount = limitAmount;
            this.account = account;
            this.challengeType = challengeType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class NChallengeInsertResponseDTO {

        private Long challengeId;

        private String title;

        private String description;

        private String status;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        private int deposit;

        private int limitAmount;

        private String challengeType;

        @Builder
        public NChallengeInsertResponseDTO(Long challengeId, String title, String description, String status,
                                           LocalDateTime startDate, LocalDateTime endDate, int deposit, int limitAmount, String challengeType)
        {
            this.challengeId = challengeId;
            this.title = title;
            this.description = description;
            this.status = status;
            this.startDate = startDate;
            this.endDate = endDate;
            this.deposit = deposit;
            this.limitAmount = limitAmount;
            this.challengeType = challengeType;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class NChallengeParticipateRequestDTO {

        private Long challengeId;

        private Long userSeq;

        private String account;

        private String challengeType;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class NChallengeUpdateDTO{
        private Long challengeId;
        private String title;
        private String description;
        private LocalDateTime startDate;
        private int limitAmount;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class NChallengeListRequestDTO {
        private String status;

        @Builder
        public NChallengeListRequestDTO(String status){
            this.status = status;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class NChallengeListResponseDTO {
        private Long challengeId;
        private String title;
        private String status;
        private int deposit;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        @Builder
        public NChallengeListResponseDTO(Long challengeId, String title, String status, int deposit, LocalDateTime startDate, LocalDateTime endDate) {
            this.challengeId = challengeId;
            this.title = title;
            this.status = status;
            this.deposit = deposit;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class NChallengeReadResponseDTO {
        private String title;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private int deposit;
        private int limitAmount;
        private Map<String, Boolean> isSuccessList;
    }
}
