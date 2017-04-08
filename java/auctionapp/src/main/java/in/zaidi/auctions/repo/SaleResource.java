package in.zaidi.auctions.repo;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import in.zaidi.engineering.auctions.api.Sale;

@FeignClient(name = "http://zuul-gateway-${service.version.zuul}", fallback = SaleResourceFallback.class)
public interface SaleResource {

	/*
	 * @RequestMapping(value = "/${service.version.sales}/sales", method =
	 * RequestMethod.GET) public List<Sale> getSales(@RequestParam("createdBy")
	 * String createdBy);
	 */

	@RequestMapping(value = "/${service.version.saleswithbid}/saleswithbid", method = RequestMethod.GET)
	public List<Sale> getSales(@RequestParam("createdBy") String createdBy, @RequestParam("lat") Float lattitude,
			@RequestParam("lon") Float longitude, @RequestParam("dist") int distance);

	@RequestMapping(value = "/${service.version.sales}/sales/{id}", method = RequestMethod.GET)
	public Sale getSaleById(@PathVariable("id") String id);

	@RequestMapping(value = "/${service.version.sales}/sales", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Sale create(@RequestBody Sale sale);
}
