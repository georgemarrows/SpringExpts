package georgemarrows.learnspring.domain;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findById(String customerId);

    void save(Customer account);
}
