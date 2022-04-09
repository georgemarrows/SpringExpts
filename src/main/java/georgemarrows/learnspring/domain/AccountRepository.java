package georgemarrows.learnspring.domain;

import java.util.List;
import java.util.Optional;

public interface AccountRepository {
    Optional<Account> findById(String accountId);

    void save(Account account);
    List<Account> listForCustomer(String id);
    
    void save(Transaction transaction);
    List<Transaction> listTransactionsForAccount(String accountId);
}
