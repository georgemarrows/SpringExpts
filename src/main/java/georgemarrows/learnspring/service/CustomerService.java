package georgemarrows.learnspring.service;

import georgemarrows.learnspring.domain.Account;
import georgemarrows.learnspring.domain.AccountRepository;
import georgemarrows.learnspring.domain.Customer;
import georgemarrows.learnspring.domain.CustomerRepository;
import georgemarrows.learnspring.domain.Transaction;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerService {

  // TODO DDD design smell to access multiple repositories here
  // It's not clear which - if either - of these should be an aggregate
  // root and hold the other.
  private final CustomerRepository customerRepository;
  private final AccountRepository accountRepository;

  @Autowired
  public CustomerService(
    CustomerRepository customerRepository,
    AccountRepository accountRepository
  ) {
    this.customerRepository = customerRepository;
    this.accountRepository = accountRepository;
  }

  public CreateAccountResult createAccount(
    String customerId,
    BigDecimal initialCredit
  ) throws NoSuchCustomer {
    Customer c = getCustomer(customerId);
    Account a = c.createAccount();
    Transaction t = a.credit(initialCredit);

    // TODO in a database transaction
    customerRepository.save(c);
    accountRepository.save(a);
    if (t != null) {
      accountRepository.save(t);
    }
    return new CreateAccountResult(a.id());
  }

  public class NoSuchCustomer extends Exception {}

  public record CreateAccountResult(String accountId) {}

  public Customer findCustomer(String customerId) throws NoSuchCustomer {
    return getCustomer(customerId);
  }

  private Customer getCustomer(String customerId) throws NoSuchCustomer {
    return customerRepository
      .findById(customerId)
      .orElseThrow(() -> new NoSuchCustomer());
  }
}
