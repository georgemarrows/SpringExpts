package georgemarrows.learnspring.domain;

import java.math.BigDecimal;
import java.util.UUID;

// DDD: this is not going to be an aggregate root for Transactions.
// Why? Because being a root implies local entities are read / saved en masse
// and that doesn't work for unbounded objects like transactions
public class Account {

    private final String id;
    private final String customerId;

    /**
     * Constructor for existing accounts
     */
    public Account(String id, String customerId) {
        this.id = id;
        this.customerId = customerId;
    }

    public static Account newForCustomer(String customerId) {
        return new Account(UUID.randomUUID().toString(), customerId);
    }
    
    public String id() {
        return id;
    }

	public String customerId() {
		return customerId;
	}

    public Transaction credit(BigDecimal amount) {
        // TODO accounting: this should reference the account being debited!
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            return Transaction.newCrediting(id(), amount);
        } else {
            return null;
        }
    }

}

