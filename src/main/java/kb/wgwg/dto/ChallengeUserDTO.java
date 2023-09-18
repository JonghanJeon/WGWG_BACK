package kb.wgwg.dto;

import lombok.*;

public class ChallengeUserDTO {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class ReadChallengeUserRequestDTO{
        private Long challengeId;
    }

    public interface ReadChallengeUserResponseDTO{
        Integer getIsSuccess();
        String getNickName();
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class CheckChallengeUserRequestDTO{
        private Long challengeId;
        private Long userSeq;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class CheckChallengeUserResponseDTO{
        private String participantType;
    }
}
