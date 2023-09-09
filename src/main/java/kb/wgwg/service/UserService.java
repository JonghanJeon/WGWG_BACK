package kb.wgwg.service;

import kb.wgwg.domain.User;
import kb.wgwg.dto.UserDTO.*;
import kb.wgwg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserReadResponseDTO readById(UserReadRequestDTO dto) {
        Optional<User> theUser = userRepository.findById(dto.getUserSeq());
        if (theUser.isEmpty()) throw new EntityNotFoundException();

        UserReadResponseDTO result = modelMapper.map(theUser.get(), UserReadResponseDTO.class);

        return result;
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteByUserSeq(id);
    }

    public void updatePassword(UserUpdateDTO dto) throws Exception {
        userRepository.updateUserPassword(dto.getPassword(), dto.getUserSeq());
    }
}
