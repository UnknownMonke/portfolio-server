package portfolioserver.core.geography;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Performs operations on the data source using JPA.
 */
public interface GeographyExposureJpaRepository  extends JpaRepository<GeographyExposure, Long> { }
