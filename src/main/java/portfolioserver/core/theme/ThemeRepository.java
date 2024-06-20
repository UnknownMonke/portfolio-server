package portfolioserver.core.theme;

import org.springframework.stereotype.Repository;

/**
 * Repository interface for the theme entity. Decoupled from implementation details.
 * <br>
 * The bean is defined through the stereotype annotation <code>@Repository</code>.
 */
@Repository
public interface ThemeRepository extends ThemeJpaRepository { }
