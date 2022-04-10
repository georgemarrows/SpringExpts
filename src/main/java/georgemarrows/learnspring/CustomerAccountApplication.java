package georgemarrows.learnspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomerAccountApplication {

  // Because the Application sits above controller, repository etc
  // it can find all components for auto-wiring. If it sits to one side,
  // you have to tell SpringBootApplication the packages to scan using
  // scanBasePackages

  public static void main(String[] args) {
    SpringApplication.run(CustomerAccountApplication.class, args);
  }
}
