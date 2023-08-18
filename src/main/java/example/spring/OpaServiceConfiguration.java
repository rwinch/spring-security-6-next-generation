package example.spring;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration(proxyBeanMethods = false)
public class OpaServiceConfiguration {

	@Bean
	OpaService opaClient(RestTemplateBuilder resetTemplateBldr) {
		String baseUri = "http://localhost:8181";
		RestClient restClient = RestClient.builder(resetTemplateBldr.build())
				.uriBuilderFactory(new DefaultUriBuilderFactory(baseUri))
				.build();
		RestClientAdapter adapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter)
				.build();

		return factory.createClient(OpaService.class);
	}

}
