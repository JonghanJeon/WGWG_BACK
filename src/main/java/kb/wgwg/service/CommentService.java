package kb.wgwg.service;

import kb.wgwg.domain.Comment;
import kb.wgwg.dto.CommentDTO.*;
import kb.wgwg.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;

    public int updateComment(CommentUpdateDTO dto){
        Comment comment = commentRepository.findById(dto.getCommentSeq()).orElseThrow(
                () -> new EntityNotFoundException("해당 댓글을 찾을 수 없습니다.")
        );

        return commentRepository.updateCommentByCommentSeq(dto.getCommentSeq(), dto.getContent());
    }

}
