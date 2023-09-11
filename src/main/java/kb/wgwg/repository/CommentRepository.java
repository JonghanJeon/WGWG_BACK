package kb.wgwg.repository;

import kb.wgwg.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Modifying
    @Query("UPDATE Comment c set c.content = :content where c.commentSeq = :commentSeq")
    int updateCommentByCommentSeq(Long commentSeq, String content);
}
