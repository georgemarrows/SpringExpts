package georgemarrows.learnspring.controller;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import georgemarrows.learnspring.domain.Transaction;
import georgemarrows.learnspring.service.AccountService;


@RestController
@RequestMapping("/api/account")
public class AccountController {

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	private final AccountService accountService;

	@Autowired
	public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

	public record Account(String customerId, BigDecimal initialCredit) {
	}

	@GetMapping
	public List<Transaction> get(@RequestParam String customerId) {
		logger.warn("GET /api/account received " + customerId);
		return accountService.listTransactions(Transaction.dummyFromAccountId);
	}

	@PostMapping
	public String set(@RequestBody Account account) {
		logger.warn("POST /api/account received " + account);
		return "success";
	}
}
