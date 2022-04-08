package georgemarrows.learnspring.domain;

import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(String accountId);

    void save(Account account);
}
