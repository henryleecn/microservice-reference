package in.zaidi.engineering.common.healthcheck;

import com.codahale.metrics.health.HealthCheck;
import com.couchbase.client.java.Bucket;

public class ServiceHealthCheck extends HealthCheck {
	private Bucket bucket;

	public ServiceHealthCheck(Bucket bucket) {
		this.bucket = bucket;
	}

	@Override
	protected Result check() throws Exception {
		if (bucket.isClosed()) {
			return Result.unhealthy("Bucket connection is closed");
		} else {
			return Result.healthy();
		}
	}

}
