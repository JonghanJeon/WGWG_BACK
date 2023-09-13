package kb.wgwg.repository;

import kb.wgwg.domain.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDateTime;


public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Page<Challenge> findAllByStatus(String status, Pageable pageable);

    @Modifying
    @Query("UPDATE NChallenge " +
            "SET title=:title, description=:description, startDate=:startDate, endDate=:startDate + 7, limitAmount=:limitAmount " +
            "WHERE challengeId = :challengeId")
    int updateChallengeByChallengeId(Long challengeId, String title, String description, LocalDateTime startDate, int limitAmount);

    @Modifying
    void deleteByChallengeId(Long challengeId);

}
