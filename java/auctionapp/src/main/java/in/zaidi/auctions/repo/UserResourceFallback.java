package in.zaidi.auctions.repo;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.ServiceUnavailableException;

import org.springframework.stereotype.Component;

import in.zaidi.engineering.auctions.api.User;

@Component
public class UserResourceFallback implements UserResource {

	@Override
	public User getById(String id) {
		throw new ServiceUnavailableException();
	}

	@Override
	public List<User> getUsers(String username) {
		return Collections.EMPTY_LIST;
	}

	@Override
	public User createUser(User user) {
		throw new ServiceUnavailableException();
	}

}
