package georgemarrows.learnspring.repository;

import georgemarrows.learnspring.domain.Customer;
import georgemarrows.learnspring.domain.CustomerRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class InMemoryCustomerRepository implements CustomerRepository {

  Map<String, Customer> store = new HashMap<>();

  {
    var c = Customer.newWithNameAndId(
      "George",
      "Marrows",
      "a fixed id for testing"
    );
    store.put(c.id(), c);
  }

  public Optional<Customer> findById(String customerId) {
    return Optional.ofNullable(store.get(customerId));
  }

  public void save(Customer customer) {
    store.put(customer.id(), customer);
  }
}
