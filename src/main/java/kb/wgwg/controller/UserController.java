package kb.wgwg.controller;

import kb.wgwg.dto.UserDTO.*;
import kb.wgwg.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> insert(UserInsertRequestDTO dto){
        if(userService.insertUser(dto))
            return ResponseEntity.ok().body("{\"status\": 200, \"success\": true, \"message\": \"회원가입 성공\"}");
        return ResponseEntity.status(400).body("{\"status\": 400, \"success\": false, \"message\": \"회원가입 실패\"}");
    }

    @DeleteMapping(value = "/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);

        return ResponseEntity.ok().build();
    }
}
