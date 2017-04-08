package in.zaidi.auctions.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import in.zaidi.auctions.repo.UserResource;

import in.zaidi.engineering.auctions.api.RoleType;
import in.zaidi.engineering.auctions.api.User;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserResource userResource;

	@Override
	public Optional<User> findByLogin(String login) {
		Map<String, String> params = new HashMap<>();
		params.put("username", login);
		List<User> user = userResource.getUsers(login);

		if (user != null && !user.isEmpty()) {
			return Optional.of(user.get(0));
		} else {
			return Optional.empty();
		}

	}

	public Optional<User> findOne(String id) {
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException();
		}
		User user = (User) userResource.getById(id);
		if (user != null){
			return Optional.of(user);
		}else{
			return Optional.empty();
		}
	}

	private class UserDetailsImpl extends User implements UserDetails {
		private static final long serialVersionUID = 1L;

		public UserDetailsImpl(User user) {
			setAccountNonExpired(user.isAccountNonExpired());
			setAddress(user.getAddress());
			setCredentialsNonExpired(user.isCredentialsNonExpired());
			setEnabled(user.isEnabled());
			setFirstName(user.getFirstName());
			setId(user.getId());
			setLastName(user.getLastName());
			setPassword(user.getPassword());
			setPhoneNumber(user.getPhoneNumber());
			setRegisteredDate(user.getRegisteredDate());
			setRoles(user.getRoles());
			setUnlocked(user.isUnlocked());
			setUsername(user.getUsername());
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return getRoles().stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
		}

		@Override
		public boolean isAccountNonLocked() {
			return isUnlocked();
		}
	}

	public UserDetails loadUserByUsername(String login) {
		Optional<User> opt = findByLogin(login);
		if(opt.isPresent()){
			return new UserDetailsImpl(opt.get());
		}else{
			return null;
		}
	}

	@Override
	public User create(User user) {
		if (user.getRoles() == null) {
			user.setRoles(new ArrayList<>(1));
			user.getRoles().add(RoleType.USER.toString());
		}
		user.setRegisteredDate(new Date());
		return userResource.createUser(user);
	}
}