package kb.wgwg.service;

import kb.wgwg.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleService {
    private final ModelMapper modelMapper;
    private final ArticleRepository repository;

    public void deleteArticle(Long id){
        repository.deleteByArticleSeq(id);
    }
}
