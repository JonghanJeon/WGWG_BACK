package kb.wgwg.repository;

import kb.wgwg.domain.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Page<Challenge> findAllByStatus(String status, Pageable pageable);


    @Modifying
    void deleteByChallengeId(Long challengeId);
}
