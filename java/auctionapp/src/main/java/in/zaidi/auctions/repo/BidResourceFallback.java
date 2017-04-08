package in.zaidi.auctions.repo;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import in.zaidi.engineering.auctions.api.Bid;
@Component
public class BidResourceFallback implements BidResource{

	@Override
	public Bid placeBid(Bid bid) {
		return null;
	}

	@Override
	public List<Bid> getBids(String saleId, boolean winning) {
		return Collections.emptyList();
	}

}
