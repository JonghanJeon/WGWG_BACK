package kb.wgwg.repository;

import kb.wgwg.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from User where email = ?1", nativeQuery = true)
    List<User> fineByEmail(String email);

    @Query(value = "select * from User where nickName = ?1", nativeQuery = true)
    List<User> findByNickName(String nickName);
}
