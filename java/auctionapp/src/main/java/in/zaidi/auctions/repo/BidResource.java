package in.zaidi.auctions.repo;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import in.zaidi.engineering.auctions.api.Bid;

@FeignClient(name = "http://zuul-gateway-${service.version.zuul}", fallback = BidResourceFallback.class)
public interface BidResource {

	@RequestMapping(value = "/${service.version.bids}/bids", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Bid placeBid(@RequestBody Bid bid);

	@RequestMapping(value = "/${service.version.bids}/bids", method = RequestMethod.GET)
	public List<Bid> getBids(@RequestParam("sale") String saleId,
			@RequestParam(value = "winning", defaultValue = "false") boolean winning);
}
