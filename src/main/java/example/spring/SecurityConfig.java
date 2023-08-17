package example.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {
	@Bean
	DefaultSecurityFilterChain springSecurity(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/user").authenticated()
				.and()
			.formLogin().and()
			.httpBasic();
		return http.build();
	}
}
