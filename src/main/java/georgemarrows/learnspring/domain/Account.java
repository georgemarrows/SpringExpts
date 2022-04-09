package georgemarrows.learnspring.domain;

import java.math.BigDecimal;

// DDD: this is not going to be an aggregate root for Transactions.
// Why? Because being a root implies local entities are read / saved en masse
// and that doesn't work for unbounded objects like transactions
public class Account {

    private final String id;

    public Account(String id) {
        this.id = id;
    }

    public String id() {
        return id;
    }

    public void credit(Account from, BigDecimal amount) {

    }

}

