package georgemarrows.learnspring.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import georgemarrows.learnspring.domain.Customer;
import georgemarrows.learnspring.domain.CustomerRepository;


@Component
public class InMemoryCustomerRepository implements CustomerRepository {

    Map<String, Customer> store = new HashMap<>();

    public Optional<Customer> findById(String customerId) {
        return Optional.ofNullable(store.get(customerId));
    }

    public void save(Customer customer) {
        store.put(customer.id(), customer);
    }
}
