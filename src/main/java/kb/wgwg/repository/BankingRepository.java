package kb.wgwg.repository;

import kb.wgwg.domain.Banking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankingRepository extends JpaRepository<Banking, Long> {
}
