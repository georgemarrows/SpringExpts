package georgemarrows.learnspring.controller;

import georgemarrows.learnspring.domain.Customer;
import georgemarrows.learnspring.domain.Transaction;
import georgemarrows.learnspring.service.AccountService;
import georgemarrows.learnspring.service.AccountService.AccountDetail;
import georgemarrows.learnspring.service.CustomerService;
import georgemarrows.learnspring.service.CustomerService.CreateAccountResult;
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

@RestController
@RequestMapping("/api/account")
public class AccountController {

  Logger logger = LoggerFactory.getLogger(AccountController.class);

  private final AccountService accountService;
  private final CustomerService customerService;

  @Autowired
  public AccountController(
    AccountService accountService,
    CustomerService customerService
  ) {
    this.accountService = accountService;
    this.customerService = customerService;
  }

  @GetMapping
  public List<Transaction> get(@RequestParam String customerId) {
    logger.warn("GET /api/account received " + customerId);
    return accountService.listTransactions(Transaction.dummyFromAccountId);
  }

  @GetMapping("/new")
  public List<AccountDetail> getNew(@RequestParam String customerId) {
    logger.warn("GET /api/account received " + customerId);

    // TODO remove this
    // Another Endpoint will output the user information showing Name, Surname,
    // balance, and transactions of the accounts.
    // Customer c = customerService.findCustomer(customerId);

    List<AccountDetail> accountDetails = accountService.listAccountsForCustomer(
      customerId
    );
    return accountDetails;
  }

  @PostMapping
  public CreateAccountResult create(@RequestBody CreateAccountRequest account) {
    // TODO this uses customerService but is on accountContoller
    logger.warn("POST /api/account received " + account);
    CreateAccountResult res = customerService.createAccount(
      account.customerId,
      account.initialCredit
    );
    return res;
  }

  public record CreateAccountRequest(
    String customerId,
    BigDecimal initialCredit
  ) {}
}
