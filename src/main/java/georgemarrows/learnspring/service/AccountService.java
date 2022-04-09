package georgemarrows.learnspring.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import georgemarrows.learnspring.domain.Account;
import georgemarrows.learnspring.domain.AccountRepository;
import georgemarrows.learnspring.domain.Transaction;

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
        return accountRepository.transactions(accountId);
    }

    public void credit(String toAccountId, String fromAccountId, BigDecimal amount) {
        // from account / to account / txn
        Account to = getAccount(toAccountId);
        Account from = getAccount(fromAccountId);
        to.credit(from, amount);
        // TODO save the accounts & transaction, transactionally
    }

    private Account getAccount(String accountId) {
        return accountRepository.findById(accountId).orElseThrow();
    }
}
