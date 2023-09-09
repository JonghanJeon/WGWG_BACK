package kb.wgwg.repository;

import kb.wgwg.domain.User;
import kb.wgwg.dto.UserDTO.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    void deleteByUserSeq(Long userSeq);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.password = :newPassword WHERE u.userSeq = :userSeq")
    void updateUserPassword(@Param("newPassword") String newPassword, @Param("userSeq") Long userSeq) throws Exception;
}
