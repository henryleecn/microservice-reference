package in.zaidi.auctions.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
//@PropertySource("classpath:/application.properties")
public class RestTemplateConfig {

	@Bean
	@Autowired
	public ClientHttpRequestFactory httpRequestFactory(HttpClient httpClient) {
		return new HttpComponentsClientHttpRequestFactory(httpClient);
	}

	@Bean
	@Autowired
	public RestTemplate restTemplate(ClientHttpRequestFactory httpRequestFactory) {
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
		// set interceptors/requestFactory
		ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
		List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
		ris.add(ri);
		restTemplate.setInterceptors(ris);
		restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

		List<HttpMessageConverter<?>> converters = restTemplate.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
				ObjectMapper mapper = new ObjectMapper();
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				jsonConverter.setObjectMapper(mapper);
				jsonConverter.setSupportedMediaTypes(Arrays.asList(
						new MediaType("application", "json", MappingJackson2HttpMessageConverter.DEFAULT_CHARSET)));
			}
		}
		return restTemplate;
	}

	@Bean
	public HttpClient httpClient() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().setConnectionManager(connectionManager)
				.build();
		return closeableHttpClient;
	}

}
