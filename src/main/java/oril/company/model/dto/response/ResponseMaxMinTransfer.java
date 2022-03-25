package oril.company.model.dto.response;

public class ResponseMaxMinTransfer {
    private String currencyPair;
    private Double price;

    public ResponseMaxMinTransfer(String currencyPair, Double price) {
        this.currencyPair = currencyPair;
        this.price = price;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public Double getPrice() {
        return price;
    }
}
