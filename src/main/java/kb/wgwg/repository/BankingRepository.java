package kb.wgwg.repository;

import kb.wgwg.domain.Banking;
import kb.wgwg.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BankingRepository extends JpaRepository<Banking, Long> {

    @Query(value = "select * from banking b where extract(year from b.banking_date) = ?1 AND extract(month from b.banking_date) = ?2 AND b.user_seq = ?3 order by b.banking_date", nativeQuery = true)
    Page<Banking> findMonth(int year, int month, Long userSeq, Pageable pageable);


    @Query(value = "SELECT category, SUM(amount) AS TOTAL " +
            "FROM banking " +
            "WHERE USER_SEQ = :userSeq AND BANKING_DATE BETWEEN TO_DATE(:checkMonth, 'YYYY-MM-DD') AND LAST_DAY(TO_DATE(:checkMonth, 'YYYY-MM-DD')) " +
            "GROUP BY category " +
            "ORDER BY TOTAL DESC" , nativeQuery = true)
    List<Object[]> readCategoryProportion(
            @Param("userSeq") Long userSeq,
            @Param("checkMonth") String checkMonth);

    @Query(value = "SELECT b.* " +
            "FROM banking b " +
            "JOIN user_entity u ON b.user_seq = u.user_seq " +
            "WHERE b.banking_date BETWEEN TO_DATE(:startDate, 'YYYY-MM-DD') AND TO_DATE(:endDate, 'YYYY-MM-DD') " +
            "AND u.user_seq = :userSeq", nativeQuery = true)
    List<Banking> findByBankingDateBetweenAndUserSeq(
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("userSeq") Long userSeq


    );
    List<Banking> findAllByOwnerAndTypeAndCategory(User theUser, String type, String category);
    List<Banking> findAllByOwnerAndCategoryAndTypeIn(User theUser, String category, List<String> types);
}
