package in.zaidi.auctions.repo;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.ServiceUnavailableException;

import org.springframework.stereotype.Component;

import in.zaidi.engineering.auctions.api.Sale;

@Component
public class SaleResourceFallback implements SaleResource {

	@Override
	public List<Sale> getSales(String createdBy, Float lat, Float lon, int distance) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public Sale getSaleById(String id) {
		throw new ServiceUnavailableException();
	}

	@Override
	public Sale create(Sale sale) {
		throw new ServiceUnavailableException();
	}

}
