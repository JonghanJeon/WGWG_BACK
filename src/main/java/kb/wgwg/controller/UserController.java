package kb.wgwg.controller;

import kb.wgwg.dto.BaseResponseDTO;
import kb.wgwg.dto.UserDTO.*;
import kb.wgwg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        result2.setMessage("실험");
        result2.setStatus(200);
        result2.setSuccess(true);
        result2.setData(result);
        return ResponseEntity.ok(result2);
    }
}
