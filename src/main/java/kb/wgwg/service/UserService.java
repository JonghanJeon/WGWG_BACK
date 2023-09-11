package kb.wgwg.service;

import kb.wgwg.domain.User;
import kb.wgwg.dto.UserDTO.*;
import kb.wgwg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserLoginResponseDTO login(UserLoginRequestDTO dto) {

        User theUser = userRepository.findByEmail(dto.getEmail());
        System.out.println(theUser);
        if(theUser == null || !theUser.getPassword().equals(dto.getPassword())) throw new EntityNotFoundException();

        UserLoginResponseDTO result = modelMapper.map(theUser, UserLoginResponseDTO.class);
        return result;
    }


    public UserReadResponseDTO readById(UserReadRequestDTO dto) {
        Optional<User> theUser = userRepository.findById(dto.getUserSeq());
        if (theUser.isEmpty()) throw new EntityNotFoundException();

        UserReadResponseDTO result = modelMapper.map(theUser.get(), UserReadResponseDTO.class);

        return result;
    }

}
