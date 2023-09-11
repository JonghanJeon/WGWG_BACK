package kb.wgwg.service;

import kb.wgwg.domain.Article;
import kb.wgwg.dto.ArticleDTO.*;
import kb.wgwg.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ModelMapper modelMapper;
    private final ArticleRepository repository;


    public void deleteArticle(Long id){
        repository.deleteByArticleSeq(id);
    }

    public ArticleUpdateDTO updateArticle(ArticleUpdateDTO dto) {
        Article article = repository.findById(dto.getArticleSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다.")
        );
        article.updateTitle(dto.getTitle());
        article.updateContent(dto.getContent());
        article.updateCategory(dto.getCategory());

        dto = modelMapper.map(article, ArticleUpdateDTO.class);
        return dto;
    }

    @Transactional(readOnly = true)
    public ArticleReadResponseDTO findArticleById(Long id) {
        Article theArticle = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다.")
        );

        return modelMapper.map(theArticle, ArticleReadResponseDTO.class);
    }

    @Transactional(readOnly = true)
    public Page<ArticleListResponseDTO> findArticlesByCategory(String category, Pageable pageable) {
        Page<Article> page = repository.findAllByCategory(category, pageable);
        Page<ArticleListResponseDTO> dtoPage = page.map(new Function<Article, ArticleListResponseDTO>() {
            @Override
            public ArticleListResponseDTO apply(Article article) {
                ArticleListResponseDTO dto = ArticleListResponseDTO.builder()
                        .articleSeq(article.getArticleSeq())
                        .title(article.getTitle())
                        .insertDate(article.getInsertDate())
                        .updateDate(article.getUpdateDate())
                        .writer("article.getWriter().getNickName()")
                        .commentSize(article.getComments().size())
                        .build();
                return dto;
            }
        });

        return dtoPage;
    }
}
