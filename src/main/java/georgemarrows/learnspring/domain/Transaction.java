package georgemarrows.learnspring.domain;

import java.math.BigDecimal;


/**
 * A simple two account transaction in an accounting system
 */
public record Transaction(
        String transactionId,
        String accountFromId,
        String accountToId,
        BigDecimal amount,
        /** balance after amount has been credited / debited */
        BigDecimal currentBalance
        // other fields (date, previous txn etc) not included
) {
    // DDD: is this a Value? You can't usually update a transaction once it's
    // posted.
}