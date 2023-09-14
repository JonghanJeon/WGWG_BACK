package kb.wgwg.repository;

import kb.wgwg.domain.ChallengeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ChallengeUserRepository extends JpaRepository<ChallengeUser, Long> {
    @Modifying
    @Query(value = "UPDATE CHALLENGE_USER cu " +
            "SET IS_SUCCESS = 0 " +
            "WHERE EXISTS ( " +
            "SELECT 1 " +
            " FROM (SELECT c.CHALLENGE_ID, n.LIMIT_AMOUNT FROM NCHALLENGE n JOIN CHALLENGE c ON n.CHALLENGE_ID = c.CHALLENGE_ID) ci " +
            " JOIN (SELECT b.USER_SEQ, cu.CHALLENGE_ID, sum(b.AMOUNT) AS spending FROM BANKING b JOIN CHALLENGE_USER cu ON b.USER_SEQ = cu.USER_ID WHERE cu.CHALLENGE_TYPE = 'N' GROUP BY USER_SEQ, cu.CHALLENGE_ID) ub " +
            " ON ci.CHALLENGE_ID = ub.CHALLENGE_ID " +
            " WHERE ci.LIMIT_AMOUNT < ub.spending " +
            " AND cu.CHALLENGE_ID = ci.CHALLENGE_ID " +
            " AND cu.USER_ID = ub.USER_SEQ)", nativeQuery = true)
    void updateChallengeUserStateOfSuccess();

    @Modifying
    @Query(value = "UPDATE CHALLENGE_USER SET IS_SUCCESS = 2 WHERE USER_ID = ?1 AND CHALLENGE_ID = ?2", nativeQuery = true)
    void updateIsSuccess(Long userSeq, Long challengeId);
}
