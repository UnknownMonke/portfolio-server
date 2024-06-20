package portfolioserver.core.equity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Performs operations on the data source using JPA.
 */
public interface EquityJpaRepository extends JpaRepository<Equity, Long> { }
