package kb.wgwg.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.dto.CommentDTO.*;
import kb.wgwg.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/comments")
public class CommentController {

    private final CommentService commentService;

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteComment(@PathVariable Long id) {

        BaseResponseDTO result = new BaseResponseDTO();
        try {
            if(commentService.deleteComment(id) > 0) {
                result.setMessage(ResponseMessage.COMMENT_DELETE_SUCCESS);
                result.setStatus(StatusCode.OK);
                result.setSuccess(true);
                return ResponseEntity.ok(result);
            } else {
                result.setMessage(ResponseMessage.NOT_FOUND_COMMENT);
                result.setStatus(StatusCode.BAD_REQUEST);
                result.setSuccess(false);
                return ResponseEntity.ok(result);
            }
        }catch (Exception e){
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            return ResponseEntity.ok(result);
        }
    }

    @PostMapping("/update")
    public ResponseEntity updateComment(@RequestBody CommentUpdateDTO dto){
        BaseResponseDTO result = new BaseResponseDTO();

        try {
            int updateRows = commentService.updateComment(dto);
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
