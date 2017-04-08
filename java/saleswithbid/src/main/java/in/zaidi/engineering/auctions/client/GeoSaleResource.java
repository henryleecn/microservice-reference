package in.zaidi.engineering.auctions.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@FeignClient(name="SalesSearch",url = "${elasticsearch.host}"/*, fallback = SaleResourceFallback.class*/)
public interface GeoSaleResource {
	@RequestMapping(value = "/sales/couchbaseDocument/_search", method = RequestMethod.GET)
	@JsonDeserialize(using= ItemDeserializer.class)
	public SalesSearchResponse getSales(@RequestBody String query);

}
