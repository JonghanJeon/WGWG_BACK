package kb.wgwg.controller;

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

    @PostMapping(value = "/read")
    public ResponseEntity<UserReadResponseDTO> readById(@RequestBody UserReadRequestDTO requestDTO) {
        UserReadResponseDTO result = userService.readById(requestDTO);
        return ResponseEntity.ok(result);
    }
}
