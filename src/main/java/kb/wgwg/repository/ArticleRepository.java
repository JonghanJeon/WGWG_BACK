package kb.wgwg.repository;

import kb.wgwg.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Modifying
    int deleteByArticleSeq(Long articleSeq);

    Page<Article> findAllByCategory(String category, Pageable pageable);
}
