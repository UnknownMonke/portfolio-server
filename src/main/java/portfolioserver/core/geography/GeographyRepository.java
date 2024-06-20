package portfolioserver.core.geography;

import org.springframework.stereotype.Repository;

/**
 * Repository interface for the geography entity. Decoupled from implementation details.
 * <br>
 * The bean is defined through the stereotype annotation <code>@Repository</code>.
 */
@Repository("geographyRepository") // Name can be omitted, same name as the interface.
public interface GeographyRepository extends GeographyJpaRepository { }
