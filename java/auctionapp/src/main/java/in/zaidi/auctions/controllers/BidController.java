package in.zaidi.auctions.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import in.zaidi.auctions.config.SecuredComponent;
import in.zaidi.auctions.services.BidService;
import in.zaidi.auctions.services.SaleService;

import in.zaidi.engineering.auctions.api.Bid;
import in.zaidi.engineering.auctions.api.Sale;

@Controller
public class BidController implements SecuredComponent {

	@Autowired
	private BidService bidService;

	@Autowired
	private SaleService saleService;

	@ResponseBody
	@RequestMapping(value = "/sales/{id}/bids", method = RequestMethod.POST, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, consumes = { MimeTypeUtils.APPLICATION_FORM_URLENCODED_VALUE,
					MimeTypeUtils.APPLICATION_JSON_VALUE })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('CSR') or hasRole('SUPERADMIN')")
	public ResponseEntity<?> placeBid(@PathVariable("id") String saleId, @RequestParam double amount) {
		if (isAnonymousUser()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		final Optional<Sale> sale = saleService.getSale(saleId);
		if (sale.isPresent() && sale.get().isOpen()) {
			final Optional<Bid> winningBid = bidService.getWinningBid(saleId);
			if (!winningBid.isPresent() || winningBid.isPresent() && (winningBid.get().getAmount() < amount)) {
				return new ResponseEntity<Bid>(bidService.bid(saleId, amount), HttpStatus.OK);
			} else {
				return new ResponseEntity<Bid>(HttpStatus.CONFLICT);
			}
		} else {
			return new ResponseEntity<Bid>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/sales/{id}/bids", method = RequestMethod.POST, consumes = {
			MimeTypeUtils.APPLICATION_FORM_URLENCODED_VALUE,
			MimeTypeUtils.TEXT_HTML_VALUE }, produces = { MimeTypeUtils.TEXT_HTML_VALUE })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('CSR') or hasRole('SUPERADMIN')")
	public String placeWebBid(@PathVariable("id") String id, double amount) {
		Optional<Sale> sale = saleService.getSale(id);
		sale.ifPresent(saleObj -> {
			if (saleObj.isOpen()) {
				final Optional<Bid> winningBid = bidService.getWinningBid(id);
				if (winningBid.isPresent() && (winningBid.get().getAmount() < amount
						&& !winningBid.get().getCreatedBy().equals(getLoggedInUser().getId()))) {
					bidService.bid(id, amount);
				}
			} else {
				throw new IllegalArgumentException("Invalid sale id");
			}
		});

		return "redirect:/sales/" + id;
	}
}
