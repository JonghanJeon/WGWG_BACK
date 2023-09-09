package kb.wgwg.repository;

import kb.wgwg.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    void deleteByUserSeq(Long userSeq);
}
