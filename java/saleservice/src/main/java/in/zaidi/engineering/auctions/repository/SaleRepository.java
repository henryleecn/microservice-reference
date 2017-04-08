package in.zaidi.engineering.auctions.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.Bucket;

import in.zaidi.engineering.auctions.api.Sale;
import in.zaidi.engineering.common.couchbase.BaseCouchbaseRepository;

@Repository
public class SaleRepository extends BaseCouchbaseRepository<Sale> {

	/**
	 * Instantiates a new bid repository.
	 *
	 * @param bucket
	 *            the bucket
	 */
	@Autowired
	public SaleRepository(@Qualifier("bucket") Bucket bucket) {
		super(bucket);
	}

}
