package in.zaidi.engineering.common.couchbase;

import com.couchbase.client.deps.com.fasterxml.jackson.databind.DeserializationFeature;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.transcoder.JsonTranscoder;

public class BaseCouchbaseRepository<T> implements CouchbaseRepository<T> {

	/** The bucket. */
	private Bucket bucket;

	/**
	 * Instantiates a new sale repository.
	 *
	 * @param bucket
	 *            the bucket
	 */
	public BaseCouchbaseRepository(Bucket bucket) {
		this.bucket = bucket;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.saleservice.repository.CouchbaseRepository#getBucket()
	 */
	@Override
	public Bucket getBucket() {
		return this.bucket;
	}

	@Override
	public JsonMapper getJsonMapper() {
		return new JsonMapper() {
			private ObjectMapper objectMapper = new ObjectMapper();
			private JsonTranscoder jsonTranscoder = new JsonTranscoder();

			@Override
			public ObjectMapper getObjectMapper() {
				objectMapper.configure(
					    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				return objectMapper;
			}

			@Override
			public JsonTranscoder getJsonTranscoder() {
				return jsonTranscoder;
			}
		};
	}
}
