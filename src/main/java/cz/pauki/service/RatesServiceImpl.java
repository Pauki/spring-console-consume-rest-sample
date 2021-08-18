package cz.pauki.service;

import cz.pauki.rest.consume.response.RatesServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Implementation of {@link RatesService}
 */
@Service
public class RatesServiceImpl implements RatesService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RatesServiceImpl.class);
    private final RestCommunicatorService restCommunicatorService;

    public RatesServiceImpl(@NotNull RestCommunicatorService restCommunicatorService) {
        Assert.notNull(restCommunicatorService, "restCommunicatorService cannot be null");
        this.restCommunicatorService = restCommunicatorService;
    }

    @Override
    public Map<String, List<RatesServiceResponse.Rate>> getLowestAndHighestRates() {
        LOGGER.debug("getLowestAndHighestRates");
        final Map<String, List<RatesServiceResponse.Rate>> response = new LinkedHashMap<>();
        response.put("HIGHEST", new ArrayList<>());
        response.put("LOWEST", new ArrayList<>());

        //call REST service for rates
        final RatesServiceResponse rates = restCommunicatorService.callForRates();

        if(rates != null && rates.getRates() != null){
            //sort rates order by standardRate
            LinkedHashMap<String, RatesServiceResponse.Rate> sortedRates =
                    rates.getRates().entrySet()
                    .stream()
                    .sorted(Comparator.comparing(stringRateEntry -> stringRateEntry.getValue().getStandardRate(), Comparator.reverseOrder()))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

            //get two highest
            sortedRates.entrySet()
                    .stream().limit(2)
                    .forEach(stringRateEntry -> response.get("HIGHEST").add(stringRateEntry.getValue()));

            //get two lowest
            sortedRates.entrySet()
                    .stream().skip(sortedRates.size() - 2 < 0 ? 0 : sortedRates.size() - 2)
                    .forEach(stringRateEntry -> response.get("LOWEST").add(stringRateEntry.getValue()));
        }

        return response;
    }
}
