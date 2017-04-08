package in.zaidi.engineering.auctions.client;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import in.zaidi.engineering.auctions.api.Sale;

@JsonDeserialize(using = ItemDeserializer.class)
public class SalesSearchResponse {
	private List<Sale> sales = new ArrayList<>();

	public List<Sale> getSales() {
		return sales;
	}

	void addSale(Sale sale) {
		sales.add(sale);
	}
}
