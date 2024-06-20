package portfolioserver.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

/**
 * Contains logger and exception mappings.
 * <br>
 * <br>
 * Controllers methods naming convention :
 * <ul>
 *     <li>Only specifies the operation name for read all, create, update or delete one.</li>
 *     <li>Else, specifies scope : <code>getOne()</code>, <code>addAll()</code>, ...</li>
 * </ul>
 */
@RestController
public class BaseController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Maps exceptions to HTTP codes.
     * <br>
     * Exceptions will be thrown at runtime during transactions then caught by the <code>@ExceptionHandler</code> annotation.
     * <br>
     * Maps <code>IllegalArgumentException</code>, <code>NoSuchElementException</code> to a 404 Not Found HTTP status code.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ IllegalArgumentException.class, NoSuchElementException.class })
    public void handleNotFound(Exception e) {
        logger.error("Exception thrown : ", e);
        // returns empty 404.
    }

    /**
     * Maps <code>MethodArgumentTypeMismatchException</code> to a 406 Not Acceptable HTTP status code.
     */
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public void handleNotAcceptable(Exception e) {
        logger.error("Exception thrown : ", e);
        // returns empty 406.
    }

    /**
     * Maps <code>DataIntegrityViolationException</code> to a 409 Conflict HTTP status code.
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({ DataIntegrityViolationException.class })
    public void handleAlreadyExists(Exception e) {
        logger.error("Exception thrown : ", e);
        // returns empty 409.
    }

    /**
     * Maps <code>UnsupportedOperationException</code> to a 501 Not Implemented HTTP status code.
     */
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler({ UnsupportedOperationException.class })
    public void handleUnableToReallocate(Exception e) {
        logger.error("Exception thrown: ", e);
        // returns empty 501.
    }
}
