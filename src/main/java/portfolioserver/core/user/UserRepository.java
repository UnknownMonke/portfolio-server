package portfolioserver.core.user;

import org.springframework.stereotype.Repository;

/**
 * Repository interface for the user entity. Decoupled from implementation details.
 * <br>
 * The bean is defined through the stereotype annotation <code>@Repository</code>.
 */
@Repository
public interface UserRepository extends UserJpaRepository { }
