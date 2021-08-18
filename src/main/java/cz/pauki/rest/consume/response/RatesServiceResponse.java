package cz.pauki.rest.consume.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.pauki.rest.RestBaseObject;

import java.util.HashMap;

/**
 * Object for 'rates' REST service response
 */
public class RatesServiceResponse extends RestBaseObject {

    private HashMap<String, Rate> rates = new HashMap<>();

    public HashMap<String, Rate> getRates() {
        return rates;
    }

    public void setRates(HashMap<String, Rate> rates) {
        this.rates = rates;
    }

    public static class Rate extends RestBaseObject {
        private String country;
        @JsonProperty("standard_rate")
        private int standardRate;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public int getStandardRate() {
            return standardRate;
        }

        public void setStandardRate(int standardRate) {
            this.standardRate = standardRate;
        }
    }
}
