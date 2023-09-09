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

    @PostMapping(value = "/read")
    public ResponseEntity<UserReadResponseDTO> readById(@RequestBody UserReadRequestDTO requestDTO) {
        UserReadResponseDTO result = userService.readById(requestDTO);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping(value = "/deleteUser/{id}")
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
        } catch (Exception e){
            result.setStatus(StatusCode.INTERNAL_SERVER_ERROR);
            result.setSuccess(false);
            result.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }
}
