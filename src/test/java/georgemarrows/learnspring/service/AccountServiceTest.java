package georgemarrows.learnspring.service;

import static org.assertj.core.api.Assertions.assertThat;

import georgemarrows.learnspring.domain.AccountRepository;
import georgemarrows.learnspring.domain.Customer;
import georgemarrows.learnspring.domain.CustomerRepository;
import georgemarrows.learnspring.repository.InMemoryAccountRepository;
import georgemarrows.learnspring.repository.InMemoryCustomerRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

public class AccountServiceTest {

  CustomerRepository customerRepository = new InMemoryCustomerRepository();
  AccountRepository accountRepository = new InMemoryAccountRepository();
  Customer c = Customer.newWithName("George", "Marrows");

  {
    customerRepository.save(c);
  }

  @Test
  public void canListAccountsForCustomer() {
    final BigDecimal amountToCredit = new BigDecimal(1000);

    // Given a new account for a customer
    CustomerService cs = new CustomerService(
      customerRepository,
      accountRepository
    );
    AccountService as = new AccountService(accountRepository);

    var createAccountResult = cs.createAccount(c.id(), amountToCredit);

    // When list the accounts
    var acDetails = as.listAccountsForCustomer(c.id());

    // Then account details are correct
    // .. 1 account with correct id
    assertThat(acDetails.size()).isEqualTo(1);

    assertThat(acDetails.get(0))
      .extracting(
        ac -> ac.accountId(),
        ac -> ac.transactions().size(),
        ac -> ac.transactions().get(0).amount()
      )
      .containsExactly(createAccountResult.accountId(), 1, amountToCredit);
  }
}
