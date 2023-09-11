package kb.wgwg.repository;

import kb.wgwg.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from USER_ENTITY where email = ?1", nativeQuery = true)
    User fineByEmail(String email);

    @Query(value = "select * from USER_ENTITY where nick_name = ?1", nativeQuery = true)
    User findByNickName(String nickName);

    @Modifying
    void deleteByUserSeq(Long userSeq);
}
