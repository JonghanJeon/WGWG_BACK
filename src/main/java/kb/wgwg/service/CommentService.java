package kb.wgwg.service;

import kb.wgwg.domain.Article;
import kb.wgwg.domain.Comment;
import kb.wgwg.domain.User;
import kb.wgwg.dto.CommentDTO.*;
import kb.wgwg.repository.ArticleRepository;
import kb.wgwg.repository.CommentRepository;
import kb.wgwg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final EntityManager entityManager;

    public CommentInsertResponseDTO insertComment(CommentInsertRequestDTO dto) {
        User user = userRepository.findById(dto.getUserSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자를 찾을 수 없습니다.")
        );

        Article article = articleRepository.findById(dto.getArticleSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 게시글을 찾을 수 없습니다.")
        );

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .writer(user)
                .parentArticle(article).build();

        Comment savedComment = commentRepository.save(comment);
        entityManager.flush();
        CommentInsertResponseDTO result = modelMapper.map(savedComment, CommentInsertResponseDTO.class);
        return result;
    }

    public int updateComment(CommentUpdateDTO dto) {
        Comment comment = commentRepository.findById(dto.getCommentSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );

        return commentRepository.updateCommentByCommentSeq(dto.getCommentSeq(), dto.getContent());
    }

    public int deleteComment(Long id){
        return commentRepository.deleteByCommentSeq(id);
    }

}
