package georgemarrows.learnspring.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerIntegrationTest {

	@Autowired
	private TestRestTemplate template;

    @Test
    public void getAccount() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/api/account?customerId={custId}", String.class, "abcdef");

		Map<String,Object>[] result = new ObjectMapper().readValue(response.getBody(), HashMap[].class);

		assertThat(result[0].get("amount")).isEqualTo(100);
	}

    @Test
    public void createAccount() throws Exception {
		var response = post("/api/account",
							"customerId", "a fixed id for testing",
							"initialCredit", "0");

		Map<String,Object> res = new ObjectMapper().readValue(response.getBody(), HashMap.class);
		assertThat(res.get("accountId")).isNotNull();
    }

	private ResponseEntity<String> post(String url, String... jsonBody) throws Exception {
		if (jsonBody.length % 2 !=0) {
			throw new Exception("can only post key/value pairs");
		}
		
		// See https://www.baeldung.com/spring-resttemplate-post-json
		var headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		var json = new JSONObject();
		for (int i = 0; i < jsonBody.length; i += 2) {
			json.put(jsonBody[i], jsonBody[i+1]);
		}

		var request = new HttpEntity<String>(json.toString(), headers);

        return template.postForEntity("/api/account", request, String.class);
	}
}