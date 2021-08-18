package cz.pauki.service;

import cz.pauki.rest.consume.response.RatesServiceResponse;

/** Interface for rest communicator service
 *
 * @author Pauki
 */
public interface RestCommunicatorService {

    /** Call rest service for getting rates
     *
     * @return rates response object or null
     */
    RatesServiceResponse callForRates();
}
