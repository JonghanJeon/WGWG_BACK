package kb.wgwg.dto;

import lombok.*;

public class UserDTO {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UserReadRequestDTO {

        private Long userSeq;

        @Builder
        public UserReadRequestDTO(Long userSeq) {
            this.userSeq = userSeq;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UserReadResponseDTO {

        private String email;

        private String nickName;

        private String userName;

        @Builder
        public UserReadResponseDTO(String email, String nickName, String userName) {
            this.email = email;
            this.nickName = nickName;
            this.userName = userName;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class UserUpdateDTO{
        private Long userSeq;
        private String password;
    }
}
