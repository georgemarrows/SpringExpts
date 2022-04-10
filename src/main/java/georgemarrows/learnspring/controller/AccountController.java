package georgemarrows.learnspring.controller;

import georgemarrows.learnspring.service.AccountService;
import georgemarrows.learnspring.service.AccountService.AccountDetail;
import georgemarrows.learnspring.service.CustomerService;
import georgemarrows.learnspring.service.CustomerService.CreateAccountResult;
import georgemarrows.learnspring.service.CustomerService.NoSuchCustomer;
import java.math.BigDecimal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountController {

  private static final Object NO_SUCH_CUSTOMER = "No such customer";

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
  public ResponseEntity<?> get(@RequestParam String customerId) {
    logger.warn("GET /api/account received " + customerId);

    try {
      var c = customerService.findCustomer(customerId);
      var res = new AccountListResult(
        c.firstName(),
        c.surname(),
        accountService.listAccountsForCustomer(customerId)
      );

      return ResponseEntity.status(HttpStatus.OK).body(res);
    } catch (NoSuchCustomer e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NO_SUCH_CUSTOMER);
    }
  }

  public record AccountListResult(
    String firstName,
    String surname,
    List<AccountDetail> accountDetails
  ) {}

  @PostMapping
  public ResponseEntity<?> create(@RequestBody CreateAccountRequest account) {
    // TODO this uses customerService but is on accountContoller
    logger.warn("POST /api/account received " + account);

    try {
      CreateAccountResult res = customerService.createAccount(
        account.customerId,
        account.initialCredit
      );
      return ResponseEntity.status(HttpStatus.OK).body(res);
    } catch (NoSuchCustomer e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(NO_SUCH_CUSTOMER);
    }
  }

  public record CreateAccountRequest(
    String customerId,
    BigDecimal initialCredit
  ) {}
}
