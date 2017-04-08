package in.zaidi.engineering.auctions.resource;

import java.util.Collections;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.zaidi.engineering.auctions.repository.BidRepository;

import in.zaidi.engineering.auctions.api.Bid;

/**
 * The Class SaleServiceResource.
 */
@Component
@Path("/bids")
public class BidResource {

	/** The bid repository. */
	@Autowired
	private BidRepository bidRepository;

	/**
	 * Gets the sale.
	 *
	 * @param saleId
	 *            the sale id
	 * @param winning
	 *            the winning
	 * @return the sale
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBid(@PathParam("id") String id) {
		Bid bid = bidRepository.findById(id);
		if (bid == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok().entity(bid).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBids(@QueryParam("sale") String saleId,
			@DefaultValue("false") @QueryParam("winning") Boolean winning) {
		if (winning) {
			return Response.ok().entity(Collections.singleton(this.bidRepository.getWinning(saleId))).build();
		}
		return Response.ok().entity(this.bidRepository.getAll(saleId)).build();
	}

	/**
	 * Creates the user.
	 *
	 * @param bid
	 *            the bid
	 * @return the response
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(Bid bid) {
		return Response.ok().entity(this.bidRepository.save(bid)).build();
	}

}