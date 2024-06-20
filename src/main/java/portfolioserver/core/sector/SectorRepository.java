package portfolioserver.core.sector;

import org.springframework.stereotype.Repository;

/**
 * Repository interface for the sector entity. Decoupled from implementation details.
 * <br>
 * The bean is defined through the stereotype annotation <code>@Repository</code>.
 */
@Repository
public interface SectorRepository extends SectorJpaRepository { }
