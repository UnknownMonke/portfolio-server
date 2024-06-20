package portfolioserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("portfolioserver.core")
public class PortfolioServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioServerApplication.class, args);
    }

}
