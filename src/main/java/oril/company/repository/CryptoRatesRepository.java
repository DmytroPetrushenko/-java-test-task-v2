package oril.company.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import oril.company.model.CryptoRatesStaticModel;

@Repository
public interface CryptoRatesRepository extends MongoRepository<CryptoRatesStaticModel, String> {
}
