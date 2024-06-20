package portfolioserver.core.sector;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Performs operations on the data source using JPA.
 */
public interface SectorJpaRepository extends JpaRepository<Sector, Long> { }
