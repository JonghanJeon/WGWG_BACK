package kb.wgwg.dto;

import lombok.*;

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
}
