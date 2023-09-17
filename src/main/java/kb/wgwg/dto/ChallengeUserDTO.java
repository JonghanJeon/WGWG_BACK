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
}
