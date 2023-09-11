package kb.wgwg.service;

import kb.wgwg.domain.Article;
import kb.wgwg.dto.ArticleDTO.*;
import kb.wgwg.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

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
}
