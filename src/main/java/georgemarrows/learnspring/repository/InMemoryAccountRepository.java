package georgemarrows.learnspring.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import georgemarrows.learnspring.domain.Account;
import georgemarrows.learnspring.domain.AccountRepository;


@Component
public class InMemoryAccountRepository implements AccountRepository {

    Map<String, Account> store = new HashMap<>();

    public Optional<Account> findById(String accountId) {
        return Optional.ofNullable(store.get(accountId));
    }

    public void save(Account account) {
        store.put(account.id(), account);
    }
}
