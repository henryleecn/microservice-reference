package in.zaidi.auctions.repo;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import in.zaidi.engineering.auctions.api.User;

@FeignClient(name = "http://zuul-gateway-${service.version.zuul}", fallback = UserResourceFallback.class)
public interface UserResource {
	
	@RequestMapping(value = "/${service.version.users}/users/{id}", method = RequestMethod.GET)
	User getById(@PathVariable("id") String id);

	@RequestMapping(value = "/${service.version.users}/users", method = RequestMethod.GET)
	List<User> getUsers(@RequestParam("username") String username);

	@RequestMapping(value = "/${service.version.users}/users", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	User createUser(@RequestBody User user);
}
