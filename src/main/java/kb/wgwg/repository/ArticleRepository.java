package kb.wgwg.repository;

import kb.wgwg.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Modifying
    void deleteByArticleSeq(Long articleSeq);
}
