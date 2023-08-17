package example.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurity6NextGenerationApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@Test
	void indexIsPublic() throws Exception {
		this.mockMvc.perform(get("/"))
				.andExpect(status().isOk());
	}

	@Test
	void userRequiresAuthentication() throws Exception {
		this.mockMvc.perform(get("/user"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser
	void userWhenAuthenticatedIsOk() throws Exception {
		this.mockMvc.perform(get("/user"))
				.andExpect(status().isOk());
	}

}
