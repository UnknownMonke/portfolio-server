package portfolioserver.core.geography;

import org.springframework.stereotype.Repository;

/**
 * Repository interface for the geography exposure entity. Decoupled from implementation details.
 * <br>
 * The bean is defined through the stereotype annotation <code>@Repository</code>.
 */
@Repository
public interface GeographyExposureRepository extends GeographyExposureJpaRepository { }
