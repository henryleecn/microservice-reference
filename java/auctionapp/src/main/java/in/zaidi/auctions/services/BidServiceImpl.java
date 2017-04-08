package in.zaidi.auctions.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.zaidi.auctions.config.SecuredComponent;
import in.zaidi.auctions.repo.BidResource;

import in.zaidi.engineering.auctions.api.Bid;

@Service
public class BidServiceImpl implements BidService, SecuredComponent {

	@Autowired
	private BidResource bidResource;

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.zaidi.auctions.services.BidService#bid(in.zaidi.auctions.
	 * entities.Sale, double)
	 */
	@Override
	public Bid bid(String saleId, double amount) {
		if (amount <= 0 || saleId == null) {
			throw new IllegalArgumentException();
		}
		Bid bid = new Bid();
		bid.setSaleId(saleId);
		bid.setAmount(amount);
		bid.setCreatedBy(getLoggedInUser().getId());
		bid.setCreatedByName(getLoggedInUser().getDisplayName());
		bid.setCreatedDate(new Date());
		return bidResource.placeBid(bid);
	}
	
	@Override
	public List<Bid> getBids(String saleId) {
		return bidResource.getBids(saleId, false);
	}

	@Override
	public Optional<Bid> getWinningBid(String saleId) {
		List<Bid> bids = bidResource.getBids(saleId, true);
		if (bids.isEmpty() || bids.get(0)==null) {
			return Optional.empty();
		} else {
			return Optional.of(bids.get(0));
		}
	}
}
