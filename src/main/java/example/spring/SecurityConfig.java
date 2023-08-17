package example.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
public class SecurityConfig {
	@Bean
	DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
		XorCsrfTokenRequestAttributeHandler requestHandler = new XorCsrfTokenRequestAttributeHandler();
		// set the name of the attribute the CsrfToken will be populated on
		requestHandler.setCsrfRequestAttributeName("_csrf");
		http
			.csrf(csrf -> csrf
				.csrfTokenRequestHandler(requestHandler)
			)
			.authorizeHttpRequests(requests -> requests
				.requestMatchers("/user").access((a,c) ->
					new AuthorizationDecision(c.getRequest().getParameterMap().containsKey("allowed"))
				)
				.anyRequest().permitAll()
			)
			.formLogin(withDefaults())
			.httpBasic(withDefaults());
		return http.build();
	}
}
