package portfolioserver.endtoend;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import portfolioserver.PortfolioServerApplication;
import portfolioserver.core.theme.Theme;
import portfolioserver.core.user.User;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <b>End-to-end tests</b> for the User API using the reactive <code>WebTestClient</code> :
 * <br>
 * <br>
 * <code>WebTestClient</code> is an HTTP client designed for testing server applications.
 * It wraps Spring’s WebClient and uses it to perform requests and exposes a testing facade for verifying responses.
 * It can also be used to test Spring MVC and Spring WebFlux applications without a running server via mock server request and response objects.
 * <br>
 * It will replace <code>TestRestTemplate</code> that will become deprecated.
 * <br>
 * <br>
 * Uses test data provided in <code>data_test.sql</code>.
 * <br>
 * <br>
 * By default, JUnit performs tests in an unpredictable order.
 * This can cause problems with data deletion and insertion as values returned by get requests may be modified,
 * or an operation could be performed on data assumed to have already been inserted.
 * <br>
 * To resolve this problem we force the test order using the <code>@TestMethodOrder</code> annotation and declaring an index.
 * <br>
 * <br>
 * Annotations :
 * <ul>
 *     <li><code>@ActiveProfiles</code> - Retrieves data source and context from profile.</li>
 *     <li><code>@SpringBootTest</code>
 *     - Uses an embedded web server listening on port 0 for the test duration with custom properties.</li>
 * </ul>
 */
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(
    classes = PortfolioServerApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.sql.init.data-locations=classpath:data_test.sql")
public class UserWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Should get all users.")
    public void get_test() {
        String url = "/users/get";

        // Assumes identity sequence from injected test data.
        List<User> expected = Collections.singletonList(
            new User(1L,
                "test",
                "test",
                "test@test.com",
                new Theme(1L, "light")));

        webTestClient
            .get()
            .uri(url)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBodyList(User.class)
            .hasSize(expected.size())
            .consumeWith(response -> { // Custom assertions.
                List<User> userList = response.getResponseBody();

                assertThat(userList == null).isFalse();

                userList.forEach(user ->
                    assertThat(expected.contains(user)).isTrue());
            });
    }

    @Test
    @Order(2)
    @DisplayName("Should get user.")
    public void getOne_test() {
        String url = "/user/{id}/get";

        User expected = new User(1L,
            "test",
            "test",
            "test@test.com",
            new Theme(1L, "light"));

        webTestClient
            .get()
            .uri(url, 1L)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBody(User.class)
            .isEqualTo(expected);
    }

    @Test
    @Order(3)
    @DisplayName("Should add user.")
    public void add_test() {
        String url = "/user/add";

        User user = new User(
            "added",
            "added",
            "added@added.com",
            null);

        // Assumes identity sequence from injected test data.
        Long expected = 2L;

        webTestClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(user)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBody(Long.class)
            .isEqualTo(expected);
    }

    @Test
    @Order(4)
    @DisplayName("Should update user.")
    public void update_test() {
        String url = "/user/{id}/update";
        Long id = 2L;

        User user = new User(id,
            "modified",
            "modified",
            "modified@modified.com",
            null);

        webTestClient
            .put()
            .uri(url, id)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(user)
            .exchange() // Performs the request.
            .expectStatus().isOk();
    }

    @Test
    @Order(5)
    @DisplayName("Should get all including added user with no theme.")
    public void getThemeEmpty_test() {
        String url = "/users/get";

        // Assumes identity sequence from injected test data.
        List<User> expected = List.of(
            new User(1L,
                "test",
                "test",
                "test@test.com",
                new Theme(1L, "light")),
            new User(
                2L,
                "modified",
                "modified",
                "modified@modified.com",
                null)
            );

        webTestClient
            .get()
            .uri(url)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBodyList(User.class)
            .hasSize(expected.size())
            .consumeWith(response -> { // Custom assertions.
                List<User> userList = response.getResponseBody();

                assertThat(userList == null).isFalse();

                userList.forEach(user ->
                    assertThat(expected.contains(user)).isTrue());
            });
    }

    @Test
    @Order(6)
    @DisplayName("Should update user with theme.")
    public void updateTheme_test() {
        String url = "/user/{id}/update";
        Long id = 2L;

        User user = new User(id,
            "modified_theme",
            "modified_theme",
            "modified_theme@modified_theme.com",
            new Theme(2L, "dark"));

        webTestClient
            .put()
            .uri(url, id)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(user)
            .exchange() // Performs the request.
            .expectStatus().isOk();
    }

    @Test
    @Order(7)
    @DisplayName("Should delete user.")
    public void delete_test() {
        String url = "/user/{id}/delete";

        webTestClient
            .delete()
            .uri(url, 2L) // Deletes previously added value.
            .exchange() // Performs the request.
            .expectStatus().isOk();
    }

    @Test
    @Order(8)
    @DisplayName("Should return code if not found by id.")
    public void notFound_test() {
        String url = "/user/{id}/delete";

        webTestClient
            .delete()
            .uri(url, 800L)
            .exchange() // Performs the request.
            .expectStatus().isNotFound();
    }

    @Test
    @Order(9)
    @DisplayName("Should return code if email already exists.")
    public void alreadyExists_test() {
        String url = "/user/add";

        User user = new User(
            "added",
            "added",
            "test@test.com",
            null);

        webTestClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(user)
            .exchange() // Performs the request.
            .expectStatus().isEqualTo(409);
    }

    @Test
    @Order(10)
    @DisplayName("Should return code if wrong id param format.")
    public void badParam_test() {
        String url = "/user/{id}/delete";

        webTestClient
            .delete()
            .uri(url, "id")
            .exchange() // Performs the request.
            .expectStatus().isEqualTo(406);
    }
}
