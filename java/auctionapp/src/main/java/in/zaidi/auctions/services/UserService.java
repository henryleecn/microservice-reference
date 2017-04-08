package in.zaidi.auctions.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

import in.zaidi.engineering.auctions.api.User;

public interface UserService {

	public Optional<User> findByLogin(String login);

	public Optional<User> findOne(String id);

	public UserDetails loadUserByUsername(String login);

	public User create(User user);

}