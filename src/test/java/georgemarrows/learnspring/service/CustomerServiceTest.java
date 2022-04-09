package georgemarrows.learnspring.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import georgemarrows.learnspring.domain.Account;
import georgemarrows.learnspring.domain.AccountRepository;
import georgemarrows.learnspring.domain.Customer;
import georgemarrows.learnspring.domain.CustomerRepository;
import georgemarrows.learnspring.domain.Transaction;
import georgemarrows.learnspring.repository.InMemoryAccountRepository;
import georgemarrows.learnspring.repository.InMemoryCustomerRepository;

public class CustomerServiceTest {

    @Test
    public void createAccountWithZeroBalance() {
    
        // Given
        CustomerRepository customerRepository = new InMemoryCustomerRepository();
        AccountRepository accountRepository = new InMemoryAccountRepository();
        Customer c = Customer.newWithName("George", "Marrows");
        customerRepository.save(c);

        CustomerService cs = new CustomerService(customerRepository, accountRepository);

        // When we create an account for the customer with zero balance
        cs.createAccount(c.id(), new BigDecimal(0));

        // .. then an account is created
        List<Account> accounts = accountRepository.listForCustomer(c.id());

        assertThat(accounts.size()).isGreaterThan(0);
        Account createdAccount = accounts.get(0);
        assertThat(createdAccount.customerId()).isEqualTo(c.id());

        // .. and there are no transactions
        List<Transaction> transactions = accountRepository.transactions(createdAccount.id());
        assertThat(transactions.size()).isEqualTo(0);

        // AccountService as = new AccountService(new InMemoryAccountRepository());
        // List<Transaction> txns = as.listTransactions("slush fund");

        // assertThat(txns.get(0).amount()).isEqualTo("100");
        // assertThat(txns.get(0).accountFromId()).isEqualTo("slush fund");
        // assertThat(txns.get(0).accountToId()).isEqualTo("YOURACCOUNT678");
    }
}
