package georgemarrows.learnspring.endtoend;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EndToEndTest {

  private static final String KNOWN_CUSTOMER_ID = "a fixed id for testing";

  @Autowired
  private TestRestTemplate template;

  @Test
  public void getAccountDetails() throws Exception {
    ResponseEntity<String> response = template.getForEntity(
      "/api/account?customerId={custId}",
      String.class,
      KNOWN_CUSTOMER_ID
    );

    Map<String, Object> result = new ObjectMapper()
      .readValue(response.getBody(), HashMap.class);

    assertThat(result)
      .extracting(
        r -> r.get("firstName"),
        r -> r.get("surname"),
        r -> ((ArrayList) r.get("accountDetails")).size() // no accounts for this customer
      )
      .containsExactly("George", "Marrows", 0);
  }

  @Test
  public void getAccountDetailsNoSuchCustomer() throws Exception {
    ResponseEntity<String> response = template.getForEntity(
      "/api/account?customerId={custId}",
      String.class,
      "there isn't a customer with this id"
    );

    assertThat(response)
      .extracting("statusCode", "body")
      .containsExactly(HttpStatus.NOT_FOUND, "No such customer");
  }

  @Test
  public void createAccount() throws Exception {
    var response = post(
      "/api/account",
      "customerId",
      KNOWN_CUSTOMER_ID,
      "initialCredit",
      "0"
    );

    Map<String, Object> res = new ObjectMapper()
      .readValue(response.getBody(), HashMap.class);
    assertThat(res.get("accountId")).isNotNull();
  }

  @Test
  public void createAccountNoSuchCustomer() throws Exception {
    var response = post(
      "/api/account",
      "customerId",
      "there isn't a customer with this id",
      "initialCredit",
      "0"
    );

    assertThat(response)
      .extracting("statusCode", "body")
      .containsExactly(HttpStatus.NOT_FOUND, "No such customer");
  }

  private ResponseEntity<String> post(String url, String... jsonBody)
    throws Exception {
    if (jsonBody.length % 2 != 0) {
      throw new Exception("can only post key/value pairs");
    }

    // See https://www.baeldung.com/spring-resttemplate-post-json
    var headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);

    var json = new JSONObject();
    for (int i = 0; i < jsonBody.length; i += 2) {
      json.put(jsonBody[i], jsonBody[i + 1]);
    }

    var request = new HttpEntity<String>(json.toString(), headers);

    return template.postForEntity("/api/account", request, String.class);
  }
}
