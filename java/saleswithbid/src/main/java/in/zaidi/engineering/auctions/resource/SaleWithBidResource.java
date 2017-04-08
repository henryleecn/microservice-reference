package in.zaidi.engineering.auctions.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import in.zaidi.engineering.auctions.client.GeoSaleResource;
import in.zaidi.engineering.auctions.client.SaleResource;
import in.zaidi.engineering.auctions.client.SalesSearchResponse;
import in.zaidi.engineering.auctions.repository.SaleWithBidRepository;

import in.zaidi.engineering.auctions.api.Sale;

@Path("/saleswithbid")
public class SaleWithBidResource {

	@Autowired
	private SaleResource resource;

	@Autowired
	private GeoSaleResource saleSearch;

	@Autowired
	private SaleWithBidRepository repository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SaleWithBidResource.class);

	/*
	 * @GET
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * getSales(@QueryParam("createdBy") String userId) { List<Sale> sales;
	 * sales = resource.getSales(userId); return
	 * Response.ok().entity(sales).build(); }
	 */

	String geoQueryTemplate = "{\"query\": {\"bool\": {\"must\": {\"match_all\": {}},\"filter\": {\"geo_distance\": "
			+ "{\"distance\": \"${distance}km\",\"doc.saleLocation\": {\"lat\": ${lat},\"lon\": ${lon}}}}}}}";

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSalesWithMaxBid(@QueryParam("createdBy") String userId, @QueryParam("lat") Float lat,
			@QueryParam("lon") Float lon, @QueryParam("dist") int km) {
		List<Sale> sales;
		List<String> saleIds = new ArrayList<String>();
		if (lat != null && lon != null) {
			sales = getSalesGeolocated(lat, lon, km);
		} else {
			sales = resource.getSales(userId);
		}
		
		for(Sale sale : sales){
			 saleIds.add(sale.getId());
			
		}
		
		LOGGER.info("sale ids " + saleIds.toString());
		
		Map<String, Double> saleWithMaxBid = repository.getSaleMaxBidUsingN1QL(userId, saleIds);

		for (Sale sale : sales) {
			if (saleWithMaxBid.containsKey(sale.getId()))
				sale.setMaxBidAmount(saleWithMaxBid.get(sale.getId()));
			else
				sale.setMaxBidAmount(sale.getStartPrice());
		}

		return Response.ok().entity(sales).build();
	}

	private List<Sale> getSalesGeolocated(Float lat, Float lon, int km) {
		List<Sale> sales;
		int distance;
		// Geolocated listing
		if (km < 1) {
			distance = 1;
		} else {
			distance = km;
		}
		Map<String, Object> data = new HashMap<>();
		data.put("distance", distance);
		data.put("lat", lat);
		data.put("lon", lon);
		SalesSearchResponse response = saleSearch.getSales(StrSubstitutor.replace(geoQueryTemplate, data));
		sales = response.getSales();
		return sales;
	}

}
