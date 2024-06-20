package portfolioserver.core.theme;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Performs operations on the data source using JPA.
 */
public interface ThemeJpaRepository extends JpaRepository<Theme, Long> { }
