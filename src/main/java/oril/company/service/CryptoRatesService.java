package oril.company.service;

import java.util.List;
import java.util.Optional;
import oril.company.model.CryptoRatesStaticModel;

public interface CryptoRatesService {
    void save(CryptoRatesStaticModel model);

    CryptoRatesStaticModel findById(String s);

    Double findMinPrice(String s);

    Double findMaxPrice(String s);

    List<Double> findPricesPage(String name, Optional<Integer> page, Optional<Integer> size);

    void getCsv();
}
