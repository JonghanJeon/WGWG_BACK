package kb.wgwg.service;

import kb.wgwg.domain.User;
import kb.wgwg.dto.UserDTO.*;
import kb.wgwg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
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

    /**
     * 이메일 중복검사 : 중복일 경우 true
     * @param email
     * @return boolean
     */
    public boolean checkEmailDup(String email){
        List<User> list = userRepository.fineByEmail(email);
        if(list != null) // email이 사용중일 경우
            return true;
        return false;
    }

    /**
     * 닉네임 중복검사 : 중복일 경우 true
     * @param nickName
     * @return boolean
     */
    public boolean checkNickNameDup(String nickName){
        List<User> list = userRepository.findByNickName(nickName);
        if(list != null) // nickName 이 사용중일 경우
            return true;
        return false;
    }

    /**
     * User테이블에 등록
     * @param dto
     */
    public void insertUser(UserInsertRequestDTO dto){
        User user = modelMapper.map(dto, User.class);
        userRepository.save(user);
    }
}
