package in.zaidi.engineering.auctions.api;

import java.beans.Transient;
import java.util.Date;
import java.util.List;

public class User implements Comparable<User> {

	private boolean accountNonExpired = true;

	private String address;

	private boolean credentialsNonExpired = true;

	private boolean enabled = true;

	private String firstName;

	private String id;

	private String lastName;

	private String password;// TODO Encrypt

	private String phoneNumber;

	private Date registeredDate;

	private List<String> roles;

	private boolean unlocked = true;

	private String username;// email

	public String getAddress() {
		return address;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getId() {
		return id;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getUsername() {
		return username;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	protected boolean isSame(User other) {
		boolean same = true;
		if (username == null) {
			if (other.username != null) {
				same = false;
			}
		} else if (!username.equals(other.username)) {
			same = false;
		}
		return same;
	}

	public boolean isUnlocked() {
		return unlocked;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setUnlocked(boolean unlocked) {
		this.unlocked = unlocked;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private boolean isNonEmpty(String value) {
		return value != null && value.trim().length() != 0;
	}

	@Transient
	public String getDisplayName() {
		String displayName = null;
		if (isNonEmpty(firstName)) {
			displayName = firstName;
		}
		if (isNonEmpty(lastName)) {
			if (displayName == null) {
				displayName = lastName;
			} else {
				displayName = firstName + " " + lastName;
			}
		}
		if (displayName == null) {
			displayName = "User:" + id;
		}
		return displayName;
	}

	@Override
	public String toString() {
		return "User [active=" + accountNonExpired + ", address=" + address + ", credentialsActive="
				+ credentialsNonExpired + ", enabled=" + enabled + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", password=" + password + ", phoneNumber=" + phoneNumber + ", registeredDate=" + registeredDate
				+ ", roles=" + roles + ", unlocked=" + unlocked + ", username=" + username + "]";
	}

	@Override
	public int compareTo(User o) {
		return o != null ? o.getUsername().compareTo(getUsername()) : 1;
	}
}
