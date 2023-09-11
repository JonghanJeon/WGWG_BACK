package kb.wgwg.controller;

import kb.wgwg.common.ResponseMessage;
import kb.wgwg.common.StatusCode;
import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.dto.UserDTO.*;
import kb.wgwg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    @PostMapping(value = "/login")
    public ResponseEntity<BaseResponseDTO> login(@RequestBody UserLoginRequestDTO requestDTO) {
        BaseResponseDTO<UserLoginResponseDTO> result2 = new BaseResponseDTO<>();
        UserLoginResponseDTO result = userService.login(requestDTO);
        result2.setMessage("로그인 성공");
        result2.setStatus(200);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }

    @PostMapping(value = "/read")
    public ResponseEntity<BaseResponseDTO> readById(@RequestBody UserReadRequestDTO requestDTO) {
        BaseResponseDTO<UserReadResponseDTO> result2 = new BaseResponseDTO<>();
        UserReadResponseDTO result = userService.readById(requestDTO);
        result2.setMessage("성공");
        result2.setStatus(200);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }


    @GetMapping("/{email}/check/email")
    public ResponseEntity<?> checkEmailDup(@PathVariable String email){
        if(userService.checkEmailDup(email))
            return ResponseEntity.status(400).body("{\"status\": 400, \"success\": false, \"message\": \"이메일 중복\"}");
        return ResponseEntity.ok().body("{\"status\": 200, \"success\": true, \"message\": \"이메일 사용 가능\"}");
    }

    @GetMapping("/{nickName}/check/nickname")
    public ResponseEntity<?> checkNickNameDup(@PathVariable String nickName){
        if(userService.checkNickNameDup(nickName))
            return ResponseEntity.status(400).body("{\"status\": 400, \"success\": false, \"message\": \"닉네임 중복\"}");
        return ResponseEntity.ok().body("{\"status\": 200, \"success\": true, \"message\": \"닉네임 사용 가능\"}");
    }

    @PostMapping("/insert")
    public ResponseEntity<?> insert(@RequestBody UserInsertRequestDTO dto){
        if(userService.insertUser(dto))
            return ResponseEntity.ok().body("{\"status\": 200, \"success\": true, \"message\": \"회원가입 성공\"}");
        return ResponseEntity.status(400).body("{\"status\": 400, \"success\": false, \"message\": \"회원가입 실패\"}");
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/update")
    public ResponseEntity updateUserInfo(@RequestBody UserUpdateDTO dto) {
        BaseResponseDTO result = new BaseResponseDTO();

        try {
            userService.updatePassword(dto);
            Map<String, Object> map = new HashMap<>();
            map.put("userSeq", dto.getUserSeq());

            result.setStatus(StatusCode.OK);
            result.setSuccess(true);
            result.setMessage(ResponseMessage.UPDATE_USER);
            result.setData(map);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
