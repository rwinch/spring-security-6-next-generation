package example.spring;

import example.spring.OpaService.Input;
import example.spring.OpaService.Request;
import example.spring.OpaService.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(OpaServiceConfiguration.class)
public class OpaServiceTests {
	@Autowired
	private OpaService client;

	@Autowired
	private MockRestServiceServer server;

	@Test
	void checkAccess() throws Exception {
		String body = """
				{
					"decision_id": "f47ad64a-00ef-4bca-9403-6edccdd626f5",
					"result": {
						"spring": {
							"allow": true,
							"grant": [
								"/user hasRole USER"
							]
						}
					}
				}
				""";
		server.expect(requestTo("http://localhost:8181/v1/data")).andRespond(withSuccess(body, MediaType.APPLICATION_JSON));
		Input input = new Input(new Request("/user"), new User("user", "ROLE_USER"));
		OpaService.Response response = client.checkAccess(input);
		assertThat(response.result().spring().allow()).isTrue();
	}

}
