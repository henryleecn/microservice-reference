package in.zaidi.auctions.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.zaidi.auctions.Constants;
import in.zaidi.auctions.config.SecuredComponent;
import in.zaidi.auctions.dto.UserDTO;
import in.zaidi.auctions.services.UserService;

import in.zaidi.engineering.auctions.api.User;

@Controller
public class UserController implements SecuredComponent {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = { "/users", "/signup" }, method = RequestMethod.POST, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE })
	public ResponseEntity<User> createUpdateUserJson(@Valid @ModelAttribute("userForm") UserDTO form,
			BindingResult bindingResult) {
		if (form.getPassword() != null && form.getConfirmPassword() != null
				&& !form.getPassword().equals(form.getConfirmPassword())) {
			bindingResult.rejectValue("password", "error.password", "Password & Confirm Password do no match");
		} else {
			validateUsernameAvailability(form, bindingResult);
		}

		if (bindingResult.hasErrors()) {
			LOGGER.debug("Showing user form with errors");
		} else {
			User user = form.toUser();
			if (isAnonymousUser()) {
				// New user
				User saved = userService.create(user);
				setAuthenticated(saved);
				return ResponseEntity.ok(saved);
			} else {
				User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (loggedInUser == null || (!loggedInUser.getId().equals(form.getId()) && !isAdminUser())) {
					// Current user can not change the posted user details
					return ResponseEntity.status(HttpStatus.FORBIDDEN).body(user);
				} else {
					return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(form);
				}
			}
			// Validate
		}
		return ResponseEntity.badRequest().body(form);
	}

	@RequestMapping(value = { "/users", "/signup" }, method = RequestMethod.POST, produces = {
			MimeTypeUtils.TEXT_HTML_VALUE })
	public String createUpdateUser(@Valid @ModelAttribute("userForm") UserDTO form, BindingResult bindingResult) {
		if (form.getPassword() != null && form.getConfirmPassword() != null
				&& !form.getPassword().equals(form.getConfirmPassword())) {
			bindingResult.rejectValue("password", "error.password", "Password & Confirm Password do no match");
		} else {
			validateUsernameAvailability(form, bindingResult);
		}

		final String view;
		if (bindingResult.hasErrors()) {
			LOGGER.debug("Showing user form with errors");
			view = Constants.VIEW_FORM_USER_SIGNUP;
		} else {
			User user = form.toUser();
			if (isAnonymousUser()) {
				// New user
				User saved = userService.create(user);
				setAuthenticated(saved);
			} else {
				User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (loggedInUser == null || (!loggedInUser.getId().equals(form.getId()) && !isAdminUser())) {
					// Current user can not change the posted user details
					throw new AccessDeniedException("Can only update own user details");
				} else {
					// Updating user non in scope yet, but this is the place
				}
			}
			// Validate

			view = Constants.VIEW_SIGNUP_CONFIRMATION;
		}
		return view;
	}

	protected void setAuthenticated(User user) {
		Authentication authenticatedUser = new PreAuthenticatedAuthenticationToken(user, user.getUsername());
		authenticatedUser.setAuthenticated(true);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = { MimeTypeUtils.TEXT_HTML_VALUE })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('CSR') or hasRole('SUPERADMIN')")
	public User getUser(@PathVariable("id") String id) {
		Optional<User> existing = userService.findByLogin(id);
		return existing.isPresent() ? existing.get() : null;
	}

	@ResponseBody
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('CSR') or hasRole('SUPERADMIN')")
	public User getUserJson(@PathVariable("id") String id) {
		Optional<User> existing = userService.findByLogin(id);
		return existing.isPresent() ? existing.get() : null;
	}

	@RequestMapping(value = { "/users", "/signup" }, method = RequestMethod.GET, produces = {
			MimeTypeUtils.TEXT_HTML_VALUE })
	public ModelAndView showUserCreateUpdateForm() {
		final UserDTO form;
		if (isAuthenticatedUser()) {
			form = UserDTO.from(getLoggedInUser());
		} else {
			// New signup form
			form = new UserDTO();
		}
		return new ModelAndView(Constants.VIEW_FORM_USER_SIGNUP, "userForm", form);
	}

	private void validateUsernameAvailability(UserDTO form, BindingResult bindingResult) {
		if (!isAuthenticatedUser()) {
			Optional<User> existing = userService.findByLogin(form.getUsername());
			if (existing.isPresent()) {
				bindingResult.rejectValue("username", "error.username", "Username is already taken!");
			}
		}
	}
}
