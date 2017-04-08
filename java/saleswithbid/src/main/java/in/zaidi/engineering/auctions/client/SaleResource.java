package in.zaidi.engineering.auctions.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import in.zaidi.engineering.auctions.api.Sale;

@FeignClient(value = "http://zuul-gateway-${service.version.zuul}", fallback = SaleResourceFallback.class)
public interface SaleResource {
	@RequestMapping(value = "/${service.version.sales}/sales", method = RequestMethod.GET)
	public List<Sale> getSales(@RequestParam("createdBy") String createdBy);

}
