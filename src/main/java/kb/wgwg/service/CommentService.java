package kb.wgwg.service;

import kb.wgwg.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
    private final ModelMapper modelMapper;
    private final CommentRepository commentRepository;


}
