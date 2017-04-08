package in.zaidi.auctions.dto;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import in.zaidi.engineering.auctions.api.User;

public class UserDTO extends User {


	// form:input - password
	@NotNull
	@Length(min = 1, max = 30)
	private String confirmPassword;

	public static UserDTO from(User user) {
		assert user != null;
		UserDTO dto = new UserDTO();
		dto.setUsername(user.getUsername());
		dto.setId(user.getId());
		dto.setFirstName(user.getFirstName());
		dto.setLastName(user.getLastName());
		dto.setPhoneNumber(user.getPhoneNumber());
		dto.setAddress(user.getAddress());
		dto.setUsername(user.getUsername());
		return dto;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	// Check if this is for New or Update
	public boolean isNew() {
		return getId() == null;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public User toUser() {
		User user = new User();
		user.setUsername(getUsername());
		user.setId(getId());
		user.setAddress(getAddress());
		user.setFirstName(getFirstName());
		user.setLastName(getLastName());
		if (getPassword() != null) {
			user.setPassword(getPassword());
		}
		user.setPhoneNumber(getPhoneNumber());
		user.setUsername(getUsername().toLowerCase());
		return user;
	}
}