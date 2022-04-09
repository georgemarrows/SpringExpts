package georgemarrows.learnspring.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import georgemarrows.learnspring.domain.Account;
import georgemarrows.learnspring.domain.AccountRepository;
import georgemarrows.learnspring.domain.Transaction;


@Component
public class InMemoryAccountRepository implements AccountRepository {

    Map<String, Account> store = new HashMap<>();
    Map<String, Transaction> txnStore = new HashMap<>();

    {
        var txn = Transaction.newCrediting("YOURACCOUNT678", BigDecimal.valueOf(100));
        txnStore.put(txn.id(), txn);
    }

    public Optional<Account> findById(String accountId) {
        return Optional.ofNullable(store.get(accountId));
    }

    public void save(Account account) {
        store.put(account.id(), account);
    }

    public List<Transaction> transactions(String accountId) {
        return txnStore.
            values().
            stream().
            filter(
                t -> (t.accountFromId().equals(accountId) || t.accountToId() == accountId)
            ).toList();
    }
}
