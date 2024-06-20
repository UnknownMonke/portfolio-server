package portfolioserver.endtoend;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import portfolioserver.PortfolioServerApplication;
import portfolioserver.common.enums.Currency;
import portfolioserver.common.enums.Product;
import portfolioserver.core.equity.Equity;
import portfolioserver.core.geography.GeographyExposure;
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
public class EquityWebClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Order(1)
    @DisplayName("Should get all equities.")
    public void get_test() {
        String url = "/equities/get";

        List<String> expectedTickerList = List.of("NVDA", "MSFT", "AIR", "TTWO", "TSLA");

        webTestClient
            .get()
            .uri(url)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBodyList(Equity.class)
            .hasSize(expectedTickerList.size())
            .consumeWith(response -> { // Custom assertions.
                List<Equity> equityList = response.getResponseBody();

                assertThat(equityList == null).isFalse();

                equityList
                    .stream()
                    .map(Equity::getTicker)
                    .forEach(ticker ->
                        assertThat(expectedTickerList.contains(ticker)).isTrue());
            });
    }

    @Test
    @Order(2)
    @DisplayName("Should get equity (with exposures).")
    public void getOne_test() {
        String url = "/equity/{id}/get";

        List<GeographyExposure> expectedGeoExposureList = List.of(
            new GeographyExposure(1L, BigDecimal.valueOf(0.45).setScale(4, RoundingMode.HALF_UP)),
            new GeographyExposure(2L, BigDecimal.valueOf(0.25).setScale(4, RoundingMode.HALF_UP)),
            new GeographyExposure(3L, BigDecimal.valueOf(0.10).setScale(4, RoundingMode.HALF_UP)),
            new GeographyExposure(4L, BigDecimal.valueOf(0.10).setScale(4, RoundingMode.HALF_UP)),
            new GeographyExposure(5L, BigDecimal.valueOf(0.10).setScale(4, RoundingMode.HALF_UP))
        );

        List<SectorExposure> expectedSectorExposureList = List.of(
            new SectorExposure(1L, BigDecimal.valueOf(0.40).setScale(4, RoundingMode.HALF_UP)),
            new SectorExposure(2L, BigDecimal.valueOf(0.60).setScale(4, RoundingMode.HALF_UP))
        );

        Equity expected = new Equity(1L, "DeGiro-1147582", "NVIDIA Corp", "NVDA", Product.STOCK, true,
            Currency.USD, 5, BigDecimal.valueOf(264.45), "Degiro", expectedGeoExposureList, expectedSectorExposureList);

        webTestClient
            .get()
            .uri(url, 1L)
            .exchange() // Performs the request.
            .expectStatus().isOk()
            .expectBody(Equity.class)
            .isEqualTo(expected);
    }

    @Test
    @Order(3)
    @DisplayName("Should delete all.")
    public void deleteAll_test() {
        String url = "/equities/delete";

        webTestClient
            .delete()
            .uri(url)
            .exchange() // Performs the request.
            .expectStatus().isOk();
    }

    @Test
    @Order(6)
    @DisplayName("Should return code if ticker already exists.")
    public void alreadyExists_test() {
        String url = "/equities/add";

        List<Equity> equityList = List.of(
            new Equity("DeGiro-1147582", "NVIDIA Corp", "MSFT", Product.STOCK, true,
                Currency.USD, 5, BigDecimal.valueOf(264.45), "Degiro"),
            new Equity("DeGiro-1147583", "MICROSOFT", "MSFT", Product.STOCK, true,
                Currency.USD, 8, BigDecimal.valueOf(461.56), "Degiro")
        );

        webTestClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(equityList)
            .exchange() // Performs the request.
            .expectStatus().isEqualTo(409);
    }

    @Test
    @Order(4)
    @DisplayName("Should add all.")
    public void addAll_test() {
        String url = "/equities/add";

        List<Equity> equityList = List.of(
            new Equity("DeGiro-1147582", "NVIDIA Corp", "NVDA", Product.STOCK, true,
                Currency.USD, 5, BigDecimal.valueOf(264.45), "Degiro"),
            new Equity("DeGiro-1147583", "MICROSOFT", "MSFT", Product.STOCK, true,
                Currency.USD, 8, BigDecimal.valueOf(461.56), "Degiro")
        );

        webTestClient
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(equityList)
            .exchange() // Performs the request.
            .expectStatus().isOk();
    }

    @Test
    @Order(5)
    @DisplayName("Should return code if not found by id.")
    public void notFound_test() {
        String url = "/equity/{id}/get";

        webTestClient
            .get()
            .uri(url, 800L)
            .exchange() // Performs the request.
            .expectStatus().isNotFound();
    }

    @Test
    @Order(8)
    @DisplayName("Should return code if wrong id param format.")
    public void badParam_test() {
        String url = "/equity/{id}/get";

        webTestClient
            .get()
            .uri(url, "id")
            .exchange() // Performs the request.
            .expectStatus().isEqualTo(406);
    }
}
