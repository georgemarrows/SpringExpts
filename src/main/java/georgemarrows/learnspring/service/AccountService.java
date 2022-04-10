package georgemarrows.learnspring.service;

import georgemarrows.learnspring.domain.Account;
import georgemarrows.learnspring.domain.AccountRepository;
import georgemarrows.learnspring.domain.Transaction;
import java.util.ArrayList;
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

  public List<AccountDetail> listAccountsForCustomer(String customerId) {
    List<AccountDetail> res = new ArrayList<>();
    for (Account acc : accountRepository.listForCustomer(customerId)) {
      String accountId = acc.id();
      AccountDetail ad = new AccountDetail(
        accountId,
        listTransactions(accountId)
      );
      res.add(ad);
    }
    return res;
  }

  public record AccountDetail(
    String accountId,
    List<Transaction> transactions
  ) {}
}
