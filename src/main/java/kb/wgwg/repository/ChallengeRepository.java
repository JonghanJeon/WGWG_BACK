package kb.wgwg.repository;

import kb.wgwg.domain.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    @Modifying
    void deleteByChallengeId(Long challengeId);
}
