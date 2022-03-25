package oril.company.service;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import oril.company.external.api.ApiConnection;
import oril.company.model.CryptoRatesStaticModel;
import oril.company.repository.CryptoRatesRepository;

@Service
public class CryptoRatesServiceImpl implements CryptoRatesService {
    private static final List<String> CURRENCY_FROM_LIST = List.of("BTC", "ETH", "XRP");
    private final CryptoRatesRepository repository;
    private final ApiConnection connection;

    public CryptoRatesServiceImpl(CryptoRatesRepository repository, ApiConnection connection) {
        this.repository = repository;
        this.connection = connection;
        init();
    }

    private void init() {
        CryptoRatesStaticModel model = new CryptoRatesStaticModel();
        for (String currencyFrom: CURRENCY_FROM_LIST) {
            model.setCurrenciesName(currencyFrom);
            model.setCurrencyExchangeStatistics(connection.createdAt(currencyFrom));
            repository.save(model);
        }
    }

    @Override
    public void save(CryptoRatesStaticModel model) {
        repository.save(model);
    }

    @Override
    public CryptoRatesStaticModel findById(String name) {
        return repository.findById(name).orElseThrow(() -> {
            throw new RuntimeException("This pair of the currencies are absent in Database");
        });
    }

    @Override
    public Double findMinPrice(String name) {
        List<Double> priceList = new ArrayList<>(findById(name)
                .getCurrencyExchangeStatistics().values());
        return Collections.min(priceList);
    }

    @Override
    public Double findMaxPrice(String name) {
        List<Double> priceList = new ArrayList<>(findById(name)
                .getCurrencyExchangeStatistics().values());
        return Collections.max(priceList);
    }

    @Override
    public List<Double> findPricesPage(String name,
                                       Optional<Integer> page,
                                       Optional<Integer> size) {
        List<Double> priceList = new ArrayList<>(findById(name)
                .getCurrencyExchangeStatistics().values());
        priceList.sort(Comparator.naturalOrder());
        int firstElement = page.orElse(0) * size.orElse(10);
        int lastElement = page.orElse(0) * size.orElse(10) + size.orElse(10);
        return priceList.subList(firstElement, lastElement);
    }

    @Override
    public void getCsv() {
        try (CSVWriter writer = new CSVWriter(new FileWriter("C:\\IdeaProjects\\Oril\\"
                + "SpringBoot_MongoDB\\src\\main\\resources\\csv\\cryptodata.csv"))) {
            writer.writeAll(creatListCsv());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<String[]> creatListCsv() {
        List<String[]> list = new ArrayList<>();
        String[] array = {"Cryptocurrency Name", "Min Price", "Max Price"};
        list.add(array);
        for (CryptoRatesStaticModel model : repository.findAll()) {
            array = new String[3];
            String currenciesName = model.getCurrenciesName();
            array[0] = currenciesName;
            array[1] = findMinPrice(currenciesName).toString();
            array[2] = findMaxPrice(currenciesName).toString();
            list.add(array);
        }
        return list;
    }
}
