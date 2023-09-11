package kb.wgwg.dto;

import lombok.*;
import kb.wgwg.domain.Comment;
import kb.wgwg.domain.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ArticleDTO {

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    @Builder
    public static final class ArticleUpdateDTO{
        private Long articleSeq;
        private String title;
        private String content;
        private String category;
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ArticleReadResponseDTO {

        private String title;

        private String content;

        private String category;

        private LocalDateTime insertDate;

        private LocalDateTime updateDate;

        private User writer;

        private List<Comment> comments;

        @Builder
        public ArticleReadResponseDTO(String title, String content, String category,
                                      LocalDateTime insertDate, LocalDateTime updateDate,
                                      User writer, List<Comment> comments)
        {
            this.title = title;
            this.content = content;
            this.category = category;
            this.insertDate = insertDate;
            this.updateDate = updateDate;
            this.writer = writer;
            this.comments = comments;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ArticleListRequestDTO {

        private String category;

        @Builder
        public ArticleListRequestDTO(String category) {
            this.category = category;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class ArticleListResponseDTO {

        private Long articleSeq;

        private String title;

        private String writer;

        private LocalDateTime insertDate;

        private LocalDateTime updateDate;

        private int commentSize;

        @Builder
        public ArticleListResponseDTO(Long articleSeq, String title, String writer,
                                      LocalDateTime insertDate, LocalDateTime updateDate, int commentSize) {
            this.articleSeq = articleSeq;
            this.title = title;
            this.writer = writer;
            this.insertDate = insertDate;
            this.updateDate = updateDate;
            this.commentSize = commentSize;
        }
    }
}
