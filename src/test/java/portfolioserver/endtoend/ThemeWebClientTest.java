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

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(
    classes = PortfolioServerApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.sql.init.data-locations=classpath:data_test.sql")
public class ThemeWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Should get all themes.")
    public void get_test() {
        String url = "/themes/get";

        // Assumes identity sequence from injected test data.
        List<Theme> expected = List.of(
            new Theme(1L, "light"),
            new Theme(2L, "dark")
        );

        webTestClient
            .get()
            .uri(url)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBodyList(Theme.class)
            .hasSize(expected.size())
            .consumeWith(response -> { // Custom assertions.
                List<Theme> themeList = response.getResponseBody();

                assertThat(themeList == null).isFalse();

                themeList.forEach(theme ->
                    assertThat(expected.contains(theme)).isTrue());
            });
    }

    @Test
    @Order(2)
    @DisplayName("Should add theme.")
    public void add_test() {
        String url = "/theme/add";
        String name = "purple";
        // Assumes identity sequence from injected test data.
        Long expected = 3L;

        webTestClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(name)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBody(Long.class)
            .isEqualTo(expected);
    }

    @Test
    @Order(3)
    @DisplayName("Should delete theme.")
    public void delete_test() {
        String url = "/theme/{id}/delete";

        webTestClient
            .delete()
            .uri(url, 1L)
            .exchange() // Performs the request.
            .expectStatus().isOk();
    }

    @Test
    @Order(4)
    @DisplayName("Should test if theme deleted in users.")
    public void deleteInUsers_test() {
        String url = "/users/get";

        // Uses users from test data.
        List<User> expected = Collections.singletonList(
            new User(1L,
                "test",
                "test",
                "test@test.com",
                null));

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
    @Order(5)
    @DisplayName("Should return code if theme not found by id.")
    public void notFound_test() {
        String url = "/theme/{id}/delete";

        webTestClient
            .delete()
            .uri(url, 800L)
            .exchange() // Performs the request.
            .expectStatus().isNotFound();
    }

    @Test
    @Order(6)
    @DisplayName("Should return code if name already exists.")
    public void alreadyExists_test() {
        String url = "/theme/add";
        String name = "dark";

        webTestClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(name)
            .exchange() // Performs the request.
            .expectStatus().isEqualTo(409);
    }

    @Test
    @Order(7)
    @DisplayName("Should return code if wrong id param format.")
    public void badParam_test() {
        String url = "/theme/{id}/delete";

        webTestClient
            .delete()
            .uri(url, "id")
            .exchange() // Performs the request.
            .expectStatus().isEqualTo(406);
    }
}
