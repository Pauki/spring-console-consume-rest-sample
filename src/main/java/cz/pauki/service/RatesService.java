package cz.pauki.service;

import cz.pauki.rest.consume.response.RatesServiceResponse;

import java.util.List;
import java.util.Map;

/**
 * Interface for rates service
 */
public interface RatesService {

    /** Return map with two keys (HIGHEST and LOWEST)
     * and list of rates
     *
     * @return map with rates list
     */
    Map<String, List<RatesServiceResponse.Rate>> getLowestAndHighestRates();
}
