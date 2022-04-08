package georgemarrows.learnspring.demo;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/account")
public class AccountController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	public record Account(String customerId, BigDecimal initialCredit) {
	}

	@GetMapping
	public String get(@RequestParam String customerId) {
		logger.warn("GET /api/account received " + customerId);
		return "customer data data to be sent here";
	}

	@PostMapping
	public String set(@RequestBody Account account) {
		logger.warn("POST /api/account received " + account);
		return "success";
	}
}
