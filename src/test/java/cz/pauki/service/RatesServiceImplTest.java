package cz.pauki.service;

import com.github.jknack.handlebars.internal.Files;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;

@SpringBootTest(properties = {"rest.url.rates:http://localhost:15500/vat-rates"},
        classes = {RestCommunicatorServiceImpl.class, RestTemplate.class})
@RunWith(SpringRunner.class)
public class RatesServiceImplTest {

    @Autowired
    private RestCommunicatorService restCommunicatorService;
    private RatesService ratesService;
    private WireMockServer wireMockServer;
    @Before
    public void init(){
        wireMockServer = new WireMockServer(15500);
        wireMockServer.start();
        ratesService = new RatesServiceImpl(restCommunicatorService);
    }
    @After
    public void after(){
        wireMockServer.stop();
    }

    @Test
    public void getLowestAndHighestRates() throws IOException {
        wireMockServer.stubFor(get("/vat-rates")
                .willReturn(ok()
                        .withHeader("Content-Type", "application/json")
                        .withBody(Files.read(getClass().getClassLoader().getResourceAsStream("rates.json"),
                                Charset.forName("UTF-8")))));

        var response = ratesService.getLowestAndHighestRates();
        Assert.assertEquals(2, response.get("HIGHEST").size());
        Assert.assertEquals("Hungary", response.get("HIGHEST").get(0).getCountry());
        Assert.assertEquals("Denmark", response.get("HIGHEST").get(1).getCountry());

        Assert.assertEquals(2, response.get("LOWEST").size());
        Assert.assertEquals("Malta", response.get("LOWEST").get(0).getCountry());
        Assert.assertEquals("Luxembourg", response.get("LOWEST").get(1).getCountry());
    }
}