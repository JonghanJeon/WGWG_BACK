package kb.wgwg.repository;

import kb.wgwg.domain.Challenge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

    Page<Challenge> findAllByStatus(String status, Pageable pageable);


    @Modifying
    void deleteByChallengeId(Long challengeId);

    @Modifying
    @Query(value = "UPDATE Challenge c SET status = '종료' WHERE TRUNC(END_DATE) < TRUNC(sysdate)", nativeQuery = true)
    void updateChallengeStateToFinish();

    @Modifying
    @Query(value = "UPDATE Challenge c SET status = '진행' WHERE TRUNC(START_DATE) = TRUNC(sysdate)", nativeQuery = true)
    void updateChallengeStateToOngoing();
}
