package in.zaidi.engineering.auctions.client;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import in.zaidi.engineering.auctions.api.Sale;

@Component
public class SaleResourceFallback implements SaleResource {

	public List<Sale> getSales(String createdBy) {
		return Collections.EMPTY_LIST;
	}
}
