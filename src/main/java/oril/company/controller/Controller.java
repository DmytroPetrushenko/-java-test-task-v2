package oril.company.controller;

import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import oril.company.model.dto.response.ResponseMaxMinTransfer;
import oril.company.model.dto.response.ResponseSortedPageTransfer;
import oril.company.service.CryptoRatesService;

@RestController
public class Controller {
    private final CryptoRatesService service;

    public Controller(CryptoRatesService service) {
        this.service = service;
    }

    @GetMapping
    public String loadDefaultPage() {
        return "Hello!";
    }

    @GetMapping("/cryptocurrencies/minprice")
    public ResponseMaxMinTransfer getMinPrice(@RequestParam String name) {
        return new ResponseMaxMinTransfer(name, service.findMinPrice(name));
    }

    @GetMapping("/cryptocurrencies/maxprice")
    public ResponseMaxMinTransfer getMaxPrice(@RequestParam String name) {
        return new ResponseMaxMinTransfer(name, service.findMaxPrice(name));
    }

    @GetMapping("/cryptocurrencies")
    public ResponseSortedPageTransfer getPricesPage(@RequestParam String name,
                                      @RequestParam Optional<Integer> page,
                                      @RequestParam Optional<Integer> size) {
        return new ResponseSortedPageTransfer(page.orElse(0), size.orElse(10),
                service.findPricesPage(name, page, size), name);
    }

    @GetMapping("/cryptocurrencies/csv")
    public void getCsv() {
        service.getCsv();
    }
}
