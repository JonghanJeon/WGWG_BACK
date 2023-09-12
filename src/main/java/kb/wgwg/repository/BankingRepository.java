package kb.wgwg.repository;

import kb.wgwg.domain.Banking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankingRepository extends JpaRepository<Banking, Long> {

    @Query(value = "select * from banking b where extract(year from b.banking_date) = ?1 AND extract(month from b.banking_date) = ?2 order by b.banking_date", nativeQuery = true)
    Page<Banking> findMonth(int year, int month, Pageable pageable);

}
