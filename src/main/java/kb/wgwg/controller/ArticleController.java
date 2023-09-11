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


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/articles")
public class ArticleController {
    private final ArticleService service;

    @PostMapping("/insert")
    public ResponseEntity<BaseResponseDTO> insertArticle(@RequestBody ArticleInsertRequestDTO requestDTO){
        BaseResponseDTO<ArticleInsertResponseDTO> result2 = new BaseResponseDTO<>();
        ArticleInsertResponseDTO result = service.insertArticle(requestDTO);
        result2.setMessage("게시글 등록 완료");
        result2.setStatus(200);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteArticle(@PathVariable Long id){
        BaseResponseDTO result = new BaseResponseDTO();
        try {
            if(service.deleteArticle(id) > 0){
                result.setStatus(StatusCode.OK);
                result.setMessage(ResponseMessage.DELETE_ARTICLE);
                result.setSuccess(true);
                return ResponseEntity.ok(result);
            }
            else{
                result.setStatus(StatusCode.BAD_REQUEST);
                result.setMessage(ResponseMessage.NOT_FOUND_ARTICLE);
                result.setSuccess(false);
                return ResponseEntity.badRequest().body(result);
            }
        } catch (Exception e){
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);

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
        ArticleReadResponseDTO result = service.findArticleById(id);
        BaseResponseDTO<ArticleReadResponseDTO> response = new BaseResponseDTO<>();
        response.setMessage("성공적으로 게시글을 불러왔습니다.");
        response.setStatus(200);
        response.setSuccess(true);
        response.setData(result);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/read")
    public ResponseEntity<BaseResponseDTO> readArticlesByCategory(@RequestBody ArticleListRequestDTO dto,
                                                                  @PageableDefault(size = 10) Pageable pageable) {
        Page<ArticleListResponseDTO> result = service.findArticlesByCategory(dto.getCategory(), pageable);
        BaseResponseDTO<Page<ArticleListResponseDTO>> response = new BaseResponseDTO<>();
        response.setMessage("성공적으로 게시글을 불러왔습니다.");
        response.setStatus(200);
        response.setSuccess(true);
        response.setData(result);

        return ResponseEntity.ok(response);
    }
}
