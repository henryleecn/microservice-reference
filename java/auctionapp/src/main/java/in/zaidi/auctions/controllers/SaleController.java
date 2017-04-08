package in.zaidi.auctions.controllers;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.zaidi.auctions.Constants;
import in.zaidi.auctions.config.SecuredComponent;
import in.zaidi.auctions.dto.SaleDTO;
import in.zaidi.auctions.services.BidService;
import in.zaidi.auctions.services.SaleService;

import in.zaidi.engineering.auctions.api.Bid;
import in.zaidi.engineering.auctions.api.Sale;

@Controller
public class SaleController implements SecuredComponent {

	private static final String BID = "bid";

	private static final String BID_URI = "bid_uri";

	private static final String BIDS = "/bids";

	private static final String ERROR_START_TIME = "error.startTime";

	private static final Logger LOGGER = LoggerFactory.getLogger(SaleController.class);

	private static final String REDIRECT = "redirect:";

	private static final String SALE = "sale";

	private static final String SALES = "sales";

	private static final String START_TIME = "startTime";

	private static final String START_TIME_MUST_BE_BEFORE_END_TIME = "Start time must be before end time";

	private static final String URI_SALES = "/sales/";

	@Autowired
	private SaleService saleService;
	@Autowired
	private BidService bidService;
	@Autowired
	private Environment env;
	
	@Value("${geolocation.distance:20}")
	private int distance;

	@RequestMapping(value = "/sales", method = RequestMethod.POST, produces = { MimeTypeUtils.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('CSR')")
	public ResponseEntity<?> createFullSaleJson(@Valid @ModelAttribute(SALE) SaleDTO sale,
			BindingResult bindingResult) {
		if (isAnonymousUser()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		LOGGER.info("Logged in user = " + getLoggedInUser());
		if (!bindingResult.hasErrors() && sale.getEndTime().before(sale.getStartTime())) {
			bindingResult.rejectValue(START_TIME, ERROR_START_TIME, START_TIME_MUST_BE_BEFORE_END_TIME);
		}
		if (bindingResult.hasErrors()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Showing sale form with errors");
			}
			return ResponseEntity.badRequest().build();
		}
		final Sale newSale = sale.getSale();
		final Optional<Sale> savedSale = saleService.create(newSale);
		return ResponseEntity.ok(savedSale.get());
	}

	@RequestMapping(value = "/sales", method = RequestMethod.POST, produces = { MimeTypeUtils.TEXT_HTML_VALUE })
	public String createFullSale(@Valid @ModelAttribute(SALE) SaleDTO sale, BindingResult bindingResult) {
		if (isAnonymousUser()) {
			return REDIRECT + URI_SALES;
		}
		LOGGER.info("Logged in user = " + getLoggedInUser());
		if (!bindingResult.hasErrors() && sale.getEndTime().before(sale.getStartTime())) {
			bindingResult.rejectValue(START_TIME, ERROR_START_TIME, START_TIME_MUST_BE_BEFORE_END_TIME);
		}
		if (bindingResult.hasErrors()) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Showing sale form with errors");
			}
			return Constants.VIEW_FORM_SALE_CREATE_FULL;
		}
		final Sale newSale = sale.getSale();
		final Sale savedSale = saleService.create(newSale).get();
		return REDIRECT + URI_SALES + savedSale.getId();
	}

	@RequestMapping(value = "/sales/{id}", method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getSaleDetailsJson(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		if (isAnonymousUser()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		final Optional<Sale> sale = saleService.getSale(id);
		if (sale.isPresent()) {
			Optional<Bid> winningBid = bidService.getWinningBid(sale.get().getId());
			if (winningBid.isPresent()) {
				sale.get().setWinningBid(winningBid.get());
			}
			return ResponseEntity.ok(sale.get());
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@RequestMapping(value = "/sales/{id}", method = RequestMethod.GET, produces = { MimeTypeUtils.TEXT_HTML_VALUE })
	public ModelAndView getSaleDetails(@PathVariable("id") String id, HttpServletRequest request,
			HttpServletResponse response) {
		final Map<String, Object> model = new HashMap<>();
		final Optional<Sale> sale = saleService.getSale(id);
		String view;
		if (sale.isPresent()) {
			Optional<Bid> winningBid = bidService.getWinningBid(sale.get().getId());
			if (winningBid.isPresent()) {
				sale.get().setWinningBid(winningBid.get());
			}
			model.put(BID_URI, request.getRequestURI() + BIDS);
			model.put(SALE, sale.orElse(null));
			model.put(BID, new Bid());
			model.put("nodeserverurl", env.getProperty("node.server.url"));
			view = Constants.VIEW_DETAILS_SALE;
		} else {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			model.put("error", "Not Found!");
			view = Constants.VIEW_ERROR;
		}
		return new ModelAndView(view, model);

	}

	@ResponseBody
	@RequestMapping(value = "/sales/{id}/bids", method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getBidsForSale(@PathVariable("id") String saleId,
			@RequestParam(value = "winning", defaultValue = "false") final boolean winningBid) {
		if (isAnonymousUser()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		final Optional<Sale> sale = saleService.getSale(saleId);
		if (!sale.isPresent()) {
			return new ResponseEntity<List<Bid>>(HttpStatus.BAD_REQUEST);
		}
		final List<Bid> bids;
		if (!winningBid) {
			bids = bidService.getBids(saleId);
		} else {
			bids = new ArrayList<>(1);
			Optional<Bid> bid = bidService.getWinningBid(saleId);
			if (bid.isPresent()) {
				bids.add(bid.get());
			}
		}
		return new ResponseEntity<List<Bid>>(bids, HttpStatus.OK);

	}

	@RequestMapping(value = { "/sales", "/" }, method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> getSalesJson(@RequestParam(required = false) String user) {
		List<Sale> sales = null;
		if (user != null) {
			sales = saleService.getSalesByUser(user, null, null,0);
		} else {
			sales = saleService.getSales(null, null, 0);
		}
		if (sales == null || sales.size() == 0) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(sales);
	}

	@Cacheable
	Map<String, String> getCurrencies() {
		return Currency.getAvailableCurrencies().stream()
				.sorted((curr1, curr2) -> curr1.getDisplayName().compareTo(curr2.getDisplayName()))
				.collect(Collectors.toMap(curr -> curr.getCurrencyCode(), curr -> curr.getDisplayName(), (u, v) -> {
					throw new IllegalStateException(String.format("Duplicate Currency Code %s", u));
				}, LinkedHashMap::new));
	}

	@RequestMapping(value = { "/sales", "/" }, method = RequestMethod.GET, produces = { MimeTypeUtils.TEXT_HTML_VALUE })
	public ModelAndView getSales(HttpServletRequest request, @RequestParam(required = false) String create,
			@RequestParam(required = false, name = "state") String stateParam,
			@RequestParam(required = false) String user, @CookieValue(value = "coordslat", required = false) Float lat,
			@CookieValue(value = "coordslon", required = false) Float lon) {
		System.out.println(lat + " " + lon);
		final Map<String, Object> model = new HashMap<>();
		final String view;
		if (create != null) {
			if (isAnonymousUser()) {
				return new ModelAndView(Constants.VIEW_LOGIN);
			}
			// Create new Sale
			model.put(SALE, new SaleDTO());
			model.put("currencies", getCurrencies());
			// model.put(ACTION_URI, request.getRequestURI());
			view = Constants.VIEW_FORM_SALE_CREATE_FULL;
		} else {
			List<Sale> sales = null;
			if (user != null) {
				sales = saleService.getSalesByUser(user, null, null, 0);
			} else {
				sales = saleService.getSales(lat, lon, distance);
			}
			model.put(SALES, sales);
			view = Constants.VIEW_LIST_SALE;
		}

		return new ModelAndView(view, model);
	}
}