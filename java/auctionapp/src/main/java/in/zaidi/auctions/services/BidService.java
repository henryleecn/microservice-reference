package in.zaidi.auctions.services;

import java.util.List;
import java.util.Optional;

import in.zaidi.engineering.auctions.api.Bid;


public interface BidService {

	Bid bid(String saleId, double amount);

	List<Bid> getBids(String saleId);

	Optional<Bid> getWinningBid(String saleId);

}