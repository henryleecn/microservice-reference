package in.zaidi.engineering.auctions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * The Class SaleServiceApplication.
 */

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"in.zaidi"})
@EnableFeignClients
@EnableCircuitBreaker
public class SaleWithBidServiceApplication {

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(SaleWithBidServiceApplication.class, args);
	}
}
