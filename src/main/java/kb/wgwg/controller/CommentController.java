package kb.wgwg.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.dto.CommentDTO.*;
import kb.wgwg.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/comments")
public class CommentController {
    private final CommentService service;

    @PostMapping("/update")
    public ResponseEntity updateComment(@RequestBody CommentUpdateDTO dto){
        BaseResponseDTO result = new BaseResponseDTO();

        try {
            int updateRows = service.updateComment(dto);
            result.setStatus(StatusCode.OK);
            result.setMessage(ResponseMessage.COMMENT_UPDATE_SUCCESS);
            result.setSuccess(true);
            result.setData(updateRows);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e){
            result.setStatus(StatusCode.BAD_REQUEST);
            result.setMessage(ResponseMessage.NOT_FOUND_COMMENT);
            result.setSuccess(false);
            return ResponseEntity.badRequest().body(result);
        } catch (Exception e){
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            return ResponseEntity.internalServerError().body(result);
        }
    }

}
