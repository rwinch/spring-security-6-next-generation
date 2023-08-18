package example.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class OpaServiceJsonTests {

	@Autowired
	JacksonTester<OpaService.Input> json;

	@Autowired
	JacksonTester<OpaService.Response> jsonResponse;

	@Test
	void deserializeResponse() throws Exception {
		String content = """
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
		assertThat(this.jsonResponse.parseObject(content).result().spring().allow()).isTrue();
	}

	@Test
	void serializeRequest() throws Exception {
		OpaService.Input input = new OpaService.Input(new OpaService.Request("/user"), new OpaService.User("user", "ROLE_USER"));
		assertThat(json.write(input)).isStrictlyEqualToJson("""
			{
				input: {
					request: { uri: "/user"},
					user: { principal: "user", authorities: ["ROLE_USER"] }
				}
			}
		""");
	}
}
