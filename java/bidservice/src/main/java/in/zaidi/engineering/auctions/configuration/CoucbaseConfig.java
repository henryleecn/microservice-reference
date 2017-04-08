package in.zaidi.engineering.auctions.configuration;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;

/**
 * The Class CoucbaseConfig.
 */
@Configuration
@RefreshScope
public class CoucbaseConfig {

	/** The bucket. */
	private Bucket bucket;

	/**
	 * Bucket supplier.
	 *
	 * @return the bucket
	 */
	@Bean(name = "bucket")
	public Bucket bucketSupplier() {
		return this.bucket;
	}

	@Value("${couchbase.host:localhost}")
	private String couchbaseHost;

	@Value("${couchbase.bucketName:default}")
	private String couchbaseBucket;

	@Value("${couchbase.bucketConnectionTimeout:5}")
	private String couchbaseBucketTimeout;

	/**
	 * Creates the bucket.
	 */
	@PostConstruct
	public void init() {
		Cluster cluster = CouchbaseCluster.create(couchbaseHost);
		this.bucket = cluster.openBucket(couchbaseBucket, Long.parseLong(couchbaseBucketTimeout), TimeUnit.SECONDS);

	}
}
