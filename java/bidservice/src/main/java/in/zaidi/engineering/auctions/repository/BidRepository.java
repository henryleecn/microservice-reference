package in.zaidi.engineering.auctions.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.Bucket;

import in.zaidi.engineering.auctions.api.Bid;
import in.zaidi.engineering.common.couchbase.BaseCouchbaseRepository;

/**
 * The Class SaleRepository.
 */
@Repository
public class BidRepository extends BaseCouchbaseRepository<Bid> {


	
	/**
	 * Instantiates a new bid repository.
	 *
	 * @param bucket the bucket
	 */
	@Autowired
	public BidRepository(@Qualifier("bucket") Bucket bucket) {
		super(bucket);
	}

	/**
	 * Gets the winning.
	 *
	 * @param saleId the sale id
	 * @return the winning
	 */
	public Bid getWinning(String saleId) {
		List<Bid> bids = getAll(saleId);
		if (bids.isEmpty()) {
			return null;
		} else {
			return bids.get(0);
		}
	}

	
	/**
	 * Gets the all.
	 *
	 * @param saleId the sale id
	 * @return the all
	 */
	public List<Bid> getAll(String saleId) {
		return getUsingView("bid", "by_sale", saleId, false, true);
	}
	
}
