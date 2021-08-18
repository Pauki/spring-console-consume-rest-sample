package cz.pauki.service;

import cz.pauki.rest.consume.response.RatesServiceResponse;
import cz.pauki.source.PropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.net.URI;

/**
 * Implementation of {@link RestCommunicatorService}
 */
@Service
public class RestCommunicatorServiceImpl implements RestCommunicatorService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestCommunicatorServiceImpl.class);

    @Value(PropertySource.REST_URL_RATES)
    private String ratesUrl;

    private RestTemplate restTemplate;

    public RestCommunicatorServiceImpl(@NotNull RestTemplate restTemplate) {
        Assert.notNull(restTemplate, "restTemplate cannot be null");
        this.restTemplate = restTemplate;
    }

    @Override
    public RatesServiceResponse callForRates() {
        LOGGER.debug("callForRates");

        var entity = restTemplate.getForEntity(URI.create(ratesUrl), RatesServiceResponse.class);
        if(entity.hasBody()){
            return entity.getBody();
        }

        return null;
    }
}
