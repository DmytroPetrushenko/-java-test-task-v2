package oril.company.model.dto.response;

import java.util.List;

public class ResponseSortedPageTransfer {
    private Integer page;
    private Integer size;
    private List<Double> sortedPriceList;
    private String currency;

    public ResponseSortedPageTransfer(Integer page, Integer size,
                                      List<Double> sortedPriceList, String currency) {
        this.page = page;
        this.size = size;
        this.sortedPriceList = sortedPriceList;
        this.currency = currency;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getElements() {
        return size;
    }

    public void setElements(Integer elements) {
        this.size = elements;
    }

    public List<Double> getSortedPriceList() {
        return sortedPriceList;
    }

    public void setSortedPriceList(List<Double> sortedPriceList) {
        this.sortedPriceList = sortedPriceList;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
