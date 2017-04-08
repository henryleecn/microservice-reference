package in.zaidi.auctions.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	// Spring Security see this :
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			LOGGER.debug("Login error:{}", error);
			model.addObject("error", "Invalid username and password!");
		}

		return model;
	}

	// Spring Security see this :
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout() {
		ModelAndView model = new ModelAndView("sale-list");
		SecurityContextHolder.clearContext();
		model.addObject("msg", "You've been logged out successfully.");
		return model;
	}
}