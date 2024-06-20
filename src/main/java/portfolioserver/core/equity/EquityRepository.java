package portfolioserver.core.equity;

import org.springframework.stereotype.Repository;

/**
 * Repository interface for the equity entity. Decoupled from implementation details.
 * <br>
 * The bean is defined through the stereotype annotation <code>@Repository</code>.
 */
@Repository
public interface EquityRepository extends EquityJpaRepository { }
