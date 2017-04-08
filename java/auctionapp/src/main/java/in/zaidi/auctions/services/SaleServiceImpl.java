package in.zaidi.auctions.services;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.zaidi.auctions.config.SecuredComponent;
import in.zaidi.auctions.repo.CoordinatesResource;
import in.zaidi.auctions.repo.SaleResource;

import in.zaidi.engineering.auctions.api.Bid;
import in.zaidi.engineering.auctions.api.Sale;

@Service
public class SaleServiceImpl implements SaleService, SecuredComponent {

	@Autowired
	private BidService bidService;

	@Autowired
	private SaleResource resource;
	
	@Autowired
	private CoordinatesResource cResource;

	public Bid bid(String saleId, double amount) {
		final Optional<Sale> sale = getSale(saleId);
		Bid bid = null;
		if (sale.isPresent()) {
			bid = bidService.bid(saleId, amount);
		} else {
			throw new IllegalArgumentException();
		}
		return bid;
	}

	@Override
	public Optional<Sale> getSale(String id) {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		Sale response = resource.getSaleById(id);
		if (response != null) {
			return Optional.of((Sale) response);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public Optional<Sale> create(Sale sale) {
		if (sale.getCreatedBy() == null) {
			sale.setCreatedBy(getLoggedInUser().getId());
			sale.setCreatedDate(new Date());
		}
		sale.setCreatedDate(new Date());
		sale.setSaleLocation(cResource.getCoordinates(sale.getCity()));
		Sale response = resource.create(sale);
		if (response != null) {
			return Optional.of((Sale) response);
		} else {
			return Optional.empty();
		}
	}

	public void setBidServiceImpl(BidServiceImpl bidServiceImpl2) {
		this.bidService = bidServiceImpl2;

	}

	@Override
	public List<Sale> getSalesByUser(String userId, Float lattitude, Float longitude, int distance) {
		Map<String, String> map = new HashMap<>();
		map.put("createdBy", userId);
		List<Sale> response = resource.getSales(userId, lattitude, longitude, distance);
		if (response != null) {
			return response;
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public List<Sale> getSales(Float lattitude, Float longitude, int distance) {
		List<Sale> response = resource.getSales(null, lattitude, longitude, distance);
		if (response != null) {
			return response;
		} else {
			return Collections.emptyList();
		}
	}
}