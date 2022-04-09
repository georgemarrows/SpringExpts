package georgemarrows.learnspring.domain;

import java.math.BigDecimal;
import java.util.UUID;


/**
 * A simple two account transaction in an accounting system.
 * 
 * DDD: is this a Value? You can't usually update a transaction once it's posted.
 */
public class Transaction {

    public static final String dummyFromAccountId = "slush fund"; // TODO
    
    // TODO these seem to need to be public to allow them to be serialised
    // automatically by @RestController. That's bad!
    public String transactionId;
    public String accountFromId;
    public String accountToId;
    public BigDecimal amount;

    private Transaction(String transactionId, String accountFromId, String accountToId, BigDecimal amount) {
        this.transactionId = transactionId;
        this.accountFromId = accountFromId;
        this.accountToId = accountToId;
        this.amount = amount;
    }

    static public Transaction newCrediting(String accountToId, BigDecimal amount) {
        return new Transaction(
            UUID.randomUUID().toString(),
            dummyFromAccountId,
            accountToId,
            amount
        );
    }


    // TODO purist DDD doesn't like getters like this becuase they can be abused
    // They're used in InMemoryAccountRepository and in tests.
    public String id() {
        return transactionId;
    }

    public String accountFromId() {
        return accountFromId;
    }

    public String accountToId() {
        return accountToId;
    }

    public BigDecimal amount() {
        return amount;
    }

}