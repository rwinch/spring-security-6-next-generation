package example.spring;

import com.fasterxml.jackson.annotation.JsonRootName;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;

public interface OpaService {
	@PostExchange("/v1/data")
	Response checkAccess(@RequestBody Input input);

	record Request(String uri) {}

	record User(String principal, String... authorities) {}

	@JsonRootName("input")
	record Input(Request request, User user) {}

	record Spring(boolean allow) {}

	record Result(Spring spring) {}
	record Response(Result result) {
	}
}
