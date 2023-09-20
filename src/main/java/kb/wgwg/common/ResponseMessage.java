package kb.wgwg.common;

public class ResponseMessage {
    public static final String INTERNAL_SERVER_ERROR = "서버 내부 에러";
    public static final String DB_ERROR = "데이터베이스 에러";

    //회원
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAIL = "로그인 실패";
    public static final String READ_USER = "회원 정보 조회 성공";
    public static final String NOT_FOUND_USER = "회원을 찾을 수 없습니다.";
    public static final String CREATED_USER = "회원 가입 성공";
    public static final String UPDATE_USER = "회원 정보 수정 성공";
    public static final String DELETE_USER = "회원 탈퇴 성공";

    //게시판
    public static final String DELETE_ARTICLE = "게시글 삭제 성공";
    public static final String NOT_FOUND_ARTICLE = "게시글을 찾을 수 없습니다.";
    public static final String COMMENT_UPDATE_SUCCESS = "댓글 수정 성공";
    public static final String NOT_FOUND_COMMENT = "댓글을 찾을 수 없습니다.";
    public static final String COMMENT_DELETE_SUCCESS = "댓글 삭제 성공";
    public static final String READ_COMMENT_SUCCESS = "댓글 조회 성공";




    //챌린지
    public static final String READ_CHALLENGELIST_SUCCESS = "챌린지 조회 성공";
    public static final String CHALLENGE_UPDATE_SUCCESS = "챌린지 수정 성공";
    public static final String NOT_FOUND_CHALLENGE = "챌린지를 찾을 수 없습니다.";


    //가계부
    public static final String READ_CATEGORY_SUCCESS = "카테고리 조회 성공";
    public static final String READ_TOTAL_SUCCESS = "가계부 수입/지출 누적 금액 조회 성공";
    
    public static final String BANKING_INSERT_SUCCESS = "입출금 등록 성공";
    public static final String BANKING_INSERT_FAIL = "입출금 등록 실패";

}
