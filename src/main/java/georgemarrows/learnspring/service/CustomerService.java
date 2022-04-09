package georgemarrows.learnspring.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import georgemarrows.learnspring.domain.Account;
import georgemarrows.learnspring.domain.AccountRepository;
import georgemarrows.learnspring.domain.Customer;
import georgemarrows.learnspring.domain.CustomerRepository;

@Component
public class CustomerService {

    // TODO DDD design smell to access multiple repositories here
    // It's not clear which - if either - of these should be an aggregate
    // root and hold the other.
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public void createAccount(String customerId, BigDecimal initialCredit) {
        Customer c = getCustomer(customerId);
        Account a = c.createAccount();
        // Transaction t = a.credit(initialCredit);
        
        // TODO in a transaction
        customerRepository.save(c);
        accountRepository.save(a);
        // accountRepository.save(t);
    }

    private Customer getCustomer(String customerId) {
        // TODO throw proper exception
        return customerRepository.findById(customerId).orElseThrow();
    }
}
