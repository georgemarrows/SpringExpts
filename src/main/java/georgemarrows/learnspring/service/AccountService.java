package georgemarrows.learnspring.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import georgemarrows.learnspring.domain.Account;
import georgemarrows.learnspring.domain.Transaction;

@Component
public class AccountService {


    public List<Transaction> listTransactions(String accountId, LocalDate from, LocalDate to) {
        // get account
        // ask account for transactions
        return Arrays.asList(
            new Transaction("123", "MYACCOUNT456", "YOURACCOUNT678", BigDecimal.valueOf(100), BigDecimal.valueOf(2100))
        );
    }

    public void credit(String toAccountId, String fromAccountId, BigDecimal amount) {
        // from account / to account / txn
        Account to = getAccount(toAccountId);
        Account from = getAccount(fromAccountId);
        to.credit(from, amount);
    }

    private Account getAccount(String toAccountId) {
        // TODO: ask repository for the account
        return null;
    }
}
