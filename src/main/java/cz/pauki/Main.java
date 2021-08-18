package cz.pauki;

import cz.pauki.service.RatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;

@SpringBootApplication
public class Main implements CommandLineRunner {
    private final static Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private final RatesService ratesService;

    public Main(@NotNull RatesService ratesService) {
        Assert.notNull(ratesService, "ratesService cannot be null");
        this.ratesService = ratesService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) {
        LOGGER.info("result {}", ratesService.getLowestAndHighestRates());
    }
}
