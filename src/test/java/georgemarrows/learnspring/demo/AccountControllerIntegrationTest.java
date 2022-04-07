package georgemarrows.learnspring.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerIntegrationTest {

	@BeforeAll
	public static void setErrorLogging() {
	   LoggingSystem.get(ClassLoader.getSystemClassLoader()).setLogLevel(Logger.ROOT_LOGGER_NAME, LogLevel.DEBUG);
	}

	Logger logger = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private TestRestTemplate template;

    @Test
    public void getAccount() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/api/account?customerId={custId}", String.class, "abcdef");
        assertThat(response.getBody()).isEqualTo("customer data data to be sent here");
    }

    @Test
    public void postAccount() throws Exception {
		// See https://www.baeldung.com/spring-resttemplate-post-json
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		var accountJson = new JSONObject();
		accountJson.put("id", 1);
		accountJson.put("name", "John");

		var request = new HttpEntity<String>(accountJson.toString(), headers);

        var response = template.postForEntity("/api/account", request, String.class);
        assertThat(response.getBody()).isEqualTo("success");
    }
}