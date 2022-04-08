package georgemarrows.learnspring.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
	scanBasePackages = {
		"georgemarrows.learnspring.controller",
		"georgemarrows.learnspring.repository",
		"georgemarrows.learnspring.service"
	}
)
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}