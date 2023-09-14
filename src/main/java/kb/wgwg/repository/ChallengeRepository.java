package kb.wgwg.repository;

import kb.wgwg.domain.Challenge;
import org.springframework.cglib.core.Local;
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
    @Query("UPDATE CoffeeChallenge " +
            "SET title=:title, description=:description, startDate=:startDate, endDate=:endDate, savingAmount=:savingAmount " +
            "WHERE challengeId = :challengeId")
    int updateChallengeByChallengeId(Long challengeId, String title, String description, LocalDateTime startDate, LocalDateTime endDate, int savingAmount);

    @Modifying
    void deleteByChallengeId(Long challengeId);

    @Modifying
    @Query(value = "UPDATE Challenge c SET status = '종료' WHERE TRUNC(END_DATE) < TRUNC(sysdate)", nativeQuery = true)
    void updateChallengeStateToFinish();

    @Modifying
    @Query(value = "UPDATE Challenge c SET status = '진행' WHERE TRUNC(START_DATE) = TRUNC(sysdate)", nativeQuery = true)
    void updateChallengeStateToOngoing();
}
