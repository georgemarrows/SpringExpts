package georgemarrows.learnspring.domain;

import java.util.UUID;

public class Customer {

    private String id;
    private String firstName;
    private String surname;

    public Customer(String firstName, String surname) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.surname = surname;
    }

    public static Customer newWithName(String firstName, String surname) {
        return new Customer(firstName, surname);
    }
    
    public String id() {
        return id;
    }

    public Account createAccount() {
        // In reality, there will be a lot of extra info and checks here
        // - what type of account: current, or some kind of savings account?
        // - is it a joint account? (Maybe joint accounts should be opened in a 
        //   different way, say as Account.create(List<Parties>)?)
        // - check that this customer can open this kind of account
        return Account.newForCustomer(id);
    }

}