package in.zaidi.engineering.auctions.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.couchbase.client.java.Bucket;

import in.zaidi.engineering.auctions.api.User;
import in.zaidi.engineering.common.couchbase.BaseCouchbaseRepository;

@Repository
public class UserRepository extends BaseCouchbaseRepository<User> {

	/**
	 * Instantiates a new bid repository.
	 *
	 * @param bucket
	 *            the bucket
	 */
	@Autowired
	public UserRepository(@Qualifier("bucket") Bucket bucket) {
		super(bucket);
	}

}
