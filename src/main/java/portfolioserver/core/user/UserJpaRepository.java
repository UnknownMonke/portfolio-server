package portfolioserver.core.user;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Performs operations on the data source using JPA.
 */
public interface UserJpaRepository extends JpaRepository<User, Long> { }
