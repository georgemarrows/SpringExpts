package georgemarrows.learnspring.service;

import georgemarrows.learnspring.domain.AccountRepository;
import georgemarrows.learnspring.domain.Transaction;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountService {

  private final AccountRepository accountRepository;

  @Autowired
  public AccountService(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public List<Transaction> listTransactions(String accountId) {
    // get account
    // ask account for transactions
    // TODO we don't have a good DDD home yet for fetching transactions
    return accountRepository.listTransactionsForAccount(accountId);
  }
}
