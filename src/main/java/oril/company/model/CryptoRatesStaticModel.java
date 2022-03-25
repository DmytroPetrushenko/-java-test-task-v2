package oril.company.model;

import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CryptoRatesStaticModel {
    @Id
    private String currenciesName;
    private Map<Integer, Double> currencyExchangeStatistics;

    public String getCurrenciesName() {
        return currenciesName;
    }

    public void setCurrenciesName(String currenciesName) {
        this.currenciesName = currenciesName;
    }

    public Map<Integer, Double> getCurrencyExchangeStatistics() {
        return currencyExchangeStatistics;
    }

    public void setCurrencyExchangeStatistics(Map<Integer, Double> currencyExchangeStatistics) {
        this.currencyExchangeStatistics = currencyExchangeStatistics;
    }
}
