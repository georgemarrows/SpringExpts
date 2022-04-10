package georgemarrows.learnspring.repository;

import georgemarrows.learnspring.domain.Account;
import georgemarrows.learnspring.domain.AccountRepository;
import georgemarrows.learnspring.domain.Transaction;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class InMemoryAccountRepository implements AccountRepository {

  Map<String, Account> store = new HashMap<>();
  Map<String, Transaction> txnStore = new HashMap<>();

  public Optional<Account> findById(String accountId) {
    return Optional.ofNullable(store.get(accountId));
  }

  public void save(Account account) {
    store.put(account.id(), account);
  }

  public List<Account> listForCustomer(String customerId) {
    return store
      .values()
      .stream()
      .filter(acc -> acc.customerId() == customerId)
      .toList();
  }

  public void save(Transaction transaction) {
    txnStore.put(transaction.id(), transaction);
  }

  public List<Transaction> listTransactionsForAccount(String accountId) {
    return txnStore
      .values()
      .stream()
      .filter(t ->
        (t.accountFromId().equals(accountId) || t.accountToId() == accountId)
      )
      .toList();
  }
}
