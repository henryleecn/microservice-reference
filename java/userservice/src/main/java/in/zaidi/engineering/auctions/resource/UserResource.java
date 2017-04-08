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
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import in.zaidi.engineering.auctions.repository.UserRepository;

import in.zaidi.engineering.auctions.api.User;

/**
 * The Class UserServiceResource.
 */
@Component
@Path("/users")
public class UserResource {

	@Autowired
	private UserRepository repository;

	/* (non-Javadoc)
	 * @see in.zaidi.engineering.auctions.resource.UR#getById(java.lang.String)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getById(@PathParam("id") String id) {
		User user = repository.findById(id);
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		} else {
			return Response.ok().entity(user).build();
		}
	}

	/* (non-Javadoc)
	 * @see in.zaidi.engineering.auctions.resource.UR#getUsers(java.lang.String)
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers(@QueryParam("username") String username) {
		List<User> users = repository.getUsingView("user", "by_username", username, false, false);
		if (username != null) {
			if (users.size() > 1) {
				return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			} else {
				if (users.size() == 1) {
					return Response.ok().entity(users).build();
				} else {
					return Response.status(Status.NOT_FOUND).build();
				}
			}
		} else {
			if (users.size() == 0) {
				return Response.status(Status.NOT_FOUND).build();
			} else {
				return Response.ok().entity(users).build();
			}
		}
	}

	/* (non-Javadoc)
	 * @see in.zaidi.engineering.auctions.resource.UR#createUser(in.zaidi.engineering.auctions.api.User)
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(User user) {
		try {
			return Response.ok().entity(repository.save(user)).build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
	}
}