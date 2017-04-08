package in.zaidi.engineering.auctions.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.zaidi.engineering.auctions.repository.SaleRepository;

import in.zaidi.engineering.auctions.api.Sale;

/**
 * The Class SaleServiceResource.
 */
@Component
@Path("/sales")
public class SaleResource {

	/** The sale repository. */
	@Autowired
	private SaleRepository repository;

	/**
	 * Gets the sale.
	 *
	 * @param userId
	 *            the user id
	 * @return the sale
	 */
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSales(@QueryParam("createdBy") String userId) {
		List<Sale> sales;
		if (userId != null && userId.length() != 0) {
			sales = repository.getUsingView("sale", "by_user", userId, false, false);
		} else {
			sales = repository.getUsingView("sale", "all", null, false, true);
		}
		return Response.ok().entity(sales).build();
	}

	/**
	 * Gets the sale by id.
	 *
	 * @param id
	 *            the id
	 * @return the sale by id
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getSaleById(@PathParam("id") String id) {
		return Response.ok().entity(repository.findById(id)).build();
	}

	/**
	 * Creates the user.
	 *
	 * @param sale
	 *            the sale
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(Sale sale) {
		return Response.ok().entity(repository.save(sale)).build();
	}

}