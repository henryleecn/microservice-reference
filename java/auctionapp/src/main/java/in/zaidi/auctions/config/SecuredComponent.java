package in.zaidi.auctions.config;

import org.springframework.security.core.context.SecurityContextHolder;

import in.zaidi.auctions.Constants;

import in.zaidi.engineering.auctions.api.RoleType;
import in.zaidi.engineering.auctions.api.User;

public interface SecuredComponent {

	default User getLoggedInUser() {
		if (isAnonymousUser()) {
			return null;
		} else {
			return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
	}

	default boolean isAnonymousUser() {
		return Constants.ANONYMOUS_USER.equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
	}

	default boolean isAuthenticatedUser() {
		return !isAnonymousUser();
	}

	default boolean isUser() {
		return isUserInRole(RoleType.USER);
	}

	default boolean isAdminUser() {
		return isUserInRole(RoleType.ADMIN);
	}

	default boolean isUserInRole(final RoleType roleType) {
		if (roleType == null) {
			return false;
		}
		final User loggedInUser = getLoggedInUser();
		
		return loggedInUser != null
				&& loggedInUser.getRoles().stream().anyMatch((String role) -> role.equals(roleType.toString()));
	}
}