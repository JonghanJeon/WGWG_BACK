package kb.wgwg.repository;

import kb.wgwg.domain.Banking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BankingRepository extends JpaRepository<Banking, Long> {

    @Query(value = "select * from banking b where extract(year from b.banking_date) = ?1 AND extract(month from b.banking_date) = ?2 order by b.banking_date", nativeQuery = true)
    Page<Banking> findMonth(int year, int month, Pageable pageable);


    @Query(value = "SELECT category, SUM(amount) AS TOTAL " +
            "FROM banking " +
            "WHERE USER_SEQ = :userSeq AND BANKING_DATE BETWEEN TO_DATE(:checkMonth, 'YYYY-MM-DD') AND LAST_DAY(TO_DATE(:checkMonth, 'YYYY-MM-DD')) " +
            "GROUP BY category " +
            "ORDER BY TOTAL DESC" , nativeQuery = true)
    List<Object[]> readCategoryProportion(
            @Param("userSeq") Long userSeq,
            @Param("checkMonth") String checkMonth);

    @Query(value = "SELECT SUM(amount) AS TOTAL " +
            "FROM banking " +
            "WHERE USER_SEQ = :userSeq AND BANKING_DATE BETWEEN TO_DATE(:checkMonth, 'YYYY-MM-DD') AND LAST_DAY(TO_DATE(:checkMonth, 'YYYY-MM-DD'))", nativeQuery = true)
    int sumTotalSpend(@Param("userSeq") Long userSeq,
                      @Param("checkMonth") String checkMonth);
}
