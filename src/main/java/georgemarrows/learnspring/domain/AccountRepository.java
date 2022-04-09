package georgemarrows.learnspring.domain;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(String accountId);

    void save(Account account);

    List<Transaction> transactions(String accountId);
}
