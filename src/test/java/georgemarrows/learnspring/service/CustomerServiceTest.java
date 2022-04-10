package georgemarrows.learnspring.service;

import static org.assertj.core.api.Assertions.assertThat;

import georgemarrows.learnspring.domain.Account;
import georgemarrows.learnspring.domain.AccountRepository;
import georgemarrows.learnspring.domain.Customer;
import georgemarrows.learnspring.domain.CustomerRepository;
import georgemarrows.learnspring.domain.Transaction;
import georgemarrows.learnspring.repository.InMemoryAccountRepository;
import georgemarrows.learnspring.repository.InMemoryCustomerRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

public class CustomerServiceTest {

  CustomerRepository customerRepository = new InMemoryCustomerRepository();
  AccountRepository accountRepository = new InMemoryAccountRepository();
  Customer c = Customer.newWithName("George", "Marrows");

  {
    customerRepository.save(c);
  }

  @Test
  public void createAccountWithZeroBalance() {
    // Given
    CustomerService cs = new CustomerService(
      customerRepository,
      accountRepository
    );

    // When we create an account for the customer with zero balance
    cs.createAccount(c.id(), new BigDecimal(0));

    // .. then an account is created
    List<Account> accounts = accountRepository.listForCustomer(c.id());

    assertThat(accounts.size()).isGreaterThan(0);
    Account createdAccount = accounts.get(0);
    assertThat(createdAccount.customerId()).isEqualTo(c.id());

    // .. and there are no transactions
    List<Transaction> transactions = accountRepository.listTransactionsForAccount(
      createdAccount.id()
    );
    assertThat(transactions.size()).isEqualTo(0);
  }

  @Test
  public void createAccountWithNonZeroBalance() {
    final BigDecimal amountToCredit = new BigDecimal(1000);

    // Given
    CustomerService cs = new CustomerService(
      customerRepository,
      accountRepository
    );

    // When we create an account for the customer with zero balance
    cs.createAccount(c.id(), amountToCredit);

    // .. then an account is created
    List<Account> accounts = accountRepository.listForCustomer(c.id());

    assertThat(accounts.size()).isEqualTo(1);
    Account createdAccount = accounts.get(0);
    assertThat(createdAccount.customerId()).isEqualTo(c.id());

    // .. and there is a transaction for the credit
    List<Transaction> transactions = accountRepository.listTransactionsForAccount(
      createdAccount.id()
    );

    assertThat(transactions.size()).isEqualTo(1);
    Transaction createdTransaction = transactions.get(0);
    assertThat(createdTransaction.accountToId()).isEqualTo(createdAccount.id());
    assertThat(createdTransaction.amount()).isEqualTo(amountToCredit);
  }
}
