package kb.wgwg.dto;

import lombok.*;

public class CommentDTO {
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class CommentUpdateDTO{
        private Long commentSeq;
        private String content;
    }
}
