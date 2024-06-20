package portfolioserver.endtoend;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import portfolioserver.PortfolioServerApplication;
import portfolioserver.core.geography.Geography;
import portfolioserver.core.geography.GeographyExposure;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(
    classes = PortfolioServerApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = "spring.sql.init.data-locations=classpath:data_test.sql")
public class GeographyWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Should get all geographies.")
    public void get_test() {
        String url = "/geographies/get";

        List<String> expectedNameList = List.of("USA", "China", "France", "Germany", "Nordics");

        webTestClient
            .get()
            .uri(url)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBodyList(Geography.class)
            .hasSize(expectedNameList.size())
            .consumeWith(response -> { // Custom assertions.
                List<Geography> geographyList = response.getResponseBody();

                assertThat(geographyList == null).isFalse();

                geographyList
                    .stream()
                    .map(Geography::getName)
                    .forEach(name ->
                    assertThat(expectedNameList.contains(name)).isTrue());
            });
    }

    @Test
    @Order(2)
    @DisplayName("Should get geography with exposures.")
    public void getOne_test() {
        String url = "/geography/{id}/get";

        List<GeographyExposure> expectedExposureList = List.of(
            new GeographyExposure(1L, BigDecimal.valueOf(0.45).setScale(4, RoundingMode.HALF_UP)),
            new GeographyExposure(6L, BigDecimal.valueOf(0.55).setScale(4, RoundingMode.HALF_UP)),
            new GeographyExposure(10L, BigDecimal.valueOf(0.30).setScale(4, RoundingMode.HALF_UP)),
            new GeographyExposure(13L, BigDecimal.valueOf(0.75).setScale(4, RoundingMode.HALF_UP)),
            new GeographyExposure(17L, BigDecimal.valueOf(0.80).setScale(4, RoundingMode.HALF_UP))
        );

        Geography expected = new Geography(1L, "USA", expectedExposureList);

        webTestClient
            .get()
            .uri(url, 1L)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBody(Geography.class)
            .isEqualTo(expected);
    }

    @Test
    @Order(3)
    @DisplayName("Should add geography.")
    public void add_test() {
        String url = "/geography/add";
        String name = "Canada";

        // Assumes identity sequence from injected test data.
        Long expected = 6L;

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
    @Order(4)
    @DisplayName("Should update geography.")
    public void update_test() {
        String url = "/geography/{id}/update";
        String name = "Scandinavia";

        webTestClient
            .put()
            .uri(url, 5L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(name)
            .exchange() // Performs the request.
            .expectStatus().isOk();
    }

    @Test
    @Order(5)
    @DisplayName("Should delete geography.")
    public void delete_test() {
        String url = "/geography/{id}/delete";

        webTestClient
            .delete()
            .uri(url, 5L)
            .exchange() // Performs the request.
            .expectStatus().isOk();
    }

    @Test
    @Order(6)
    @DisplayName("Should return code if not found by id.")
    public void notFound_test() {
        String url = "/geography/{id}/delete";

        webTestClient
            .delete()
            .uri(url, 800L)
            .exchange() // Performs the request.
            .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
    @DisplayName("Should return code if name already exists.")
    public void alreadyExists_test() {
        String url = "/geography/{id}/update";
        String name = "USA";

        webTestClient
            .put()
            .uri(url, 4L)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(name)
            .exchange() // Performs the request.
            .expectStatus().isEqualTo(409);
    }

    @Test
    @Order(8)
    @DisplayName("Should return code if wrong id param format.")
    public void badParam_test() {
        String url = "/geography/{id}/delete";

        webTestClient
            .delete()
            .uri(url, "id")
            .exchange() // Performs the request.
            .expectStatus().isEqualTo(406);
    }
}
