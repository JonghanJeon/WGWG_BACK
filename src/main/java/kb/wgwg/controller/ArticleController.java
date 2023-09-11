package kb.wgwg.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.dto.ArticleDTO.*;
import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

import static kb.wgwg.common.ResponseMessage.INTERNAL_SERVER_ERROR;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/articles")
public class ArticleController {
    private final ArticleService service;
    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteArticle(@PathVariable Long id){
        BaseResponseDTO result = new BaseResponseDTO();
        try {
            service.deleteArticle(id);
            result.setStatus(StatusCode.OK);
            result.setMessage(ResponseMessage.DELETE_ARTICLE);
            result.setSuccess(true);
            return ResponseEntity.ok(result);
        } catch (Exception e){
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            result.setMessage(INTERNAL_SERVER_ERROR);

            return ResponseEntity.internalServerError().body(result);
        }
    }

    @PostMapping(value = "/update")
    public ResponseEntity updateArticle(@RequestBody ArticleUpdateDTO dto) {
        BaseResponseDTO<ArticleUpdateDTO> result2 = new BaseResponseDTO<>();
        ArticleUpdateDTO result = service.updateArticle(dto);
        result2.setMessage("성공");
        result2.setStatus(200);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);

    }

    @GetMapping(value = "/read/{id}")
    public ResponseEntity<BaseResponseDTO> readArticle(@PathVariable Long id) {
        BaseResponseDTO<ArticleReadResponseDTO> response = new BaseResponseDTO<>();

        try {
            ArticleReadResponseDTO result = service.findArticleById(id);
            response.setMessage("성공적으로 게시글을 불러왔습니다.");
            response.setStatus(200);
            response.setSuccess(true);
            response.setData(result);

        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(404);
            response.setSuccess(false);
        } catch (Exception e) {
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(500);
            response.setSuccess(false);
        }

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/read")
    public ResponseEntity<BaseResponseDTO> readArticlesByCategory(@RequestBody ArticleListRequestDTO dto,
                                                                  @PageableDefault(size = 10) Pageable pageable) {
        BaseResponseDTO<Page<ArticleListResponseDTO>> response = new BaseResponseDTO<>();

        try {
            Page<ArticleListResponseDTO> result = service.findArticlesByCategory(dto.getCategory(), pageable);
            response.setMessage("성공적으로 게시글을 불러왔습니다.");
            response.setStatus(200);
            response.setSuccess(true);
            response.setData(result);

        } catch (EntityNotFoundException e) {
            response.setMessage(e.getMessage());
            response.setStatus(404);
            response.setSuccess(false);
        } catch (Exception e) {
            response.setMessage(INTERNAL_SERVER_ERROR);
            response.setStatus(500);
            response.setSuccess(false);
        }

        return ResponseEntity.ok(response);
    }
}
