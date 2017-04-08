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
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;

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

	@Value("${couchbase.bucketConnectionTimeout:5000}")
	private String couchbaseBucketTimeout;
	
	@Value("${couchbase.viewTimeout:5000}")
	private String viewTimeout;
	
	@Value("${couchbase.autoReleaseAfter:3000}")
	private String autoReleaseAfter;

	/**
	 * Creates the bucket.
	 */
	@PostConstruct
	public void init() {
		CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
		        .connectTimeout(Long.parseLong(couchbaseBucketTimeout))//10000ms = 10s, default is 5s
		        .viewTimeout(Long.parseLong(viewTimeout))
		        .autoreleaseAfter(Long.parseLong(autoReleaseAfter)) //5000ms = 5s, default is 2s
		        .build();
		Cluster cluster = CouchbaseCluster.create(env, couchbaseHost);
		//this.bucket = cluster.openBucket(couchbaseBucket, Long.parseLong(couchbaseBucketTimeout), TimeUnit.SECONDS);
		this.bucket = cluster.openBucket(couchbaseBucket);

	}
}
