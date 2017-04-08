package in.zaidi.auctions.services;

import java.util.List;
import java.util.Optional;

import in.zaidi.engineering.auctions.api.Bid;
import in.zaidi.engineering.auctions.api.Sale;

public interface SaleService {

	Bid bid(String saleId, double amount);

	Optional<Sale> getSale(String id);

	Optional<Sale> create(Sale sale);

	List<Sale> getSalesByUser(String userId, Float lattitude, Float longitude, int distance);

	List<Sale> getSales(Float lattitude, Float longitude, int distance);

}
