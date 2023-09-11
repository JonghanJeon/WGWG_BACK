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
@Transactional
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


    @Transactional(readOnly = true)
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
        User user = userRepository.findByEmail(email);
        if(user != null) // email이 사용중일 경우
            return true;
        return false;
    }

    /**
     * 닉네임 중복검사 : 중복일 경우 true
     * @param nickName
     * @return boolean
     */
    public boolean checkNickNameDup(String nickName){
        User user = userRepository.findByNickName(nickName);
        if(user != null) // nickName 이 사용중일 경우
            return true;
        return false;
    }

    /**
     * User테이블에 등록 : 성공시 true
     * @param dto
     * @return boolean
     */
    public boolean insertUser(UserInsertRequestDTO dto) {
        User user = modelMapper.map(dto, User.class);
        try {
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("userService " + e.getMessage());
        }
        return false;
    }

    public void deleteUser(Long id) {
        userRepository.deleteByUserSeq(id);
    }
}
