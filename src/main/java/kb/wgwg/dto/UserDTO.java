package kb.wgwg.dto;

import lombok.*;

public class UserDTO {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UserLoginRequestDTO {
        private String email;
        private String password;

        @Builder
        public UserLoginRequestDTO(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class UserLoginResponseDTO {
        private Long userSeq;
        private String nickName;

        @Builder
        public UserLoginResponseDTO(Long userSeq, String nickName){
            this.userSeq = userSeq;
            this.nickName = nickName;
        }
    }

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
    public static final class UserInsertRequestDTO {
        private String userName;

        private String email;

        private String nickName;

        private String password;
        @Builder
        public UserInsertRequestDTO(String userName, String email, String nickName, String password) {
            this.email = email;
            this.nickName = nickName;
            this.userName = userName;
            this.password = password;
        }
    }
}
