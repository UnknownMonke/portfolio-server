package portfolioserver.endtoend;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import portfolioserver.PortfolioServerApplication;
import portfolioserver.core.sector.Sector;
import portfolioserver.core.sector.SectorExposure;

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
public class SectorWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Should get all sectors.")
    public void get_test() {
        String url = "/sectors/get";

        List<String> expectedNameList = List.of(
            "Technology",
            "Industrials",
            "Video Games",
            "Hardware & Peripherals",
            "IT & Services",
            "Automotive",
            "Aeronautics");

        webTestClient
            .get()
            .uri(url)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBodyList(Sector.class)
            .hasSize(expectedNameList.size())
            .consumeWith(response -> { // Custom assertions.
                List<Sector> sectorList = response.getResponseBody();

                assertThat(sectorList == null).isFalse();

                sectorList
                    .stream()
                    .map(Sector::getName)
                    .forEach(name ->
                        assertThat(expectedNameList.contains(name)).isTrue());
            });
    }

    @Test
    @Order(2)
    @DisplayName("Should get sector (with exposures).")
    public void getOne_test() {
        String url = "/sector/{id}/get";

        List<SectorExposure> expectedExposureList = List.of(
            new SectorExposure(1L, BigDecimal.valueOf(0.40).setScale(4, RoundingMode.HALF_UP)),
            new SectorExposure(3L, BigDecimal.valueOf(0.30).setScale(4, RoundingMode.HALF_UP)),
            new SectorExposure(6L, BigDecimal.valueOf(1.00).setScale(4, RoundingMode.HALF_UP))
        );

        Sector expected = new Sector(3L, "Video Games", 1, 1, expectedExposureList);

        webTestClient
            .get()
            .uri(url, 3L)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBody(Sector.class)
            .isEqualTo(expected);
    }

    @Test
    @Order(3)
    @DisplayName("Should add sector.")
    public void add_test() {
        String url = "/sector/add";
        Sector sector = new Sector("Consumer Staples", 0, null);

        // Assumes identity sequence from injected test data.
        Long expected = 8L;

        webTestClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(sector)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBody(Long.class)
            .isEqualTo(expected);
    }

    @Test
    @Order(4)
    @DisplayName("Should update sector.")
    public void update_test() {
        String url = "/sector/{id}/update";
        String name = "Updated";

        webTestClient
            .put()
            .uri(url, 8L)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(name)
            .exchange() // Performs the request.
            .expectStatus().isOk();
    }

    @Test
    @Order(5)
    @DisplayName("Should delete sector.")
    public void delete_test() {
        String url = "/sector/{id}/delete";

        webTestClient
            .delete()
            .uri(url, 8L)
            .exchange() // Performs the request.
            .expectStatus().isOk();
    }

    @Test
    @Order(6)
    @DisplayName("Should return code if not found by id.")
    public void notFound_test() {
        String url = "/sector/{id}/delete";

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
        String url = "/sector/{id}/update";
        String name = "Automotive";

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
        String url = "/sector/{id}/delete";

        webTestClient
            .delete()
            .uri(url, "id")
            .exchange() // Performs the request.
            .expectStatus().isEqualTo(406);
    }
}
