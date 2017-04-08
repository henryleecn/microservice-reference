package in.zaidi.auctions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import feign.Logger;

@Configuration
@ComponentScan(basePackages = "in.zaidi")
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Configuration
	public class FooConfiguration {
	    @Bean
	    Logger.Level feignLoggerLevel() {
	        return Logger.Level.FULL;
	    }
	}

	private static final int SECONDS_30 = 30;

	private static final long SECONDS_5 = 5 * 1000L;

	private static final int STATIC_CACHE_PERIOD = 31556926;

	@Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/" ).setViewName( "forward:/sales" );
        registry.setOrder( Ordered.HIGHEST_PRECEDENCE );
        super.addViewControllers( registry );
    }
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
				.setCachePeriod(STATIC_CACHE_PERIOD);
		registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCachePeriod(STATIC_CACHE_PERIOD);
	}

	@Override
	public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
		configurer.setDefaultTimeout(SECONDS_5);
	}

	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		configurer.favorPathExtension(false).useJaf(false).ignoreAcceptHeader(false)
				.mediaType("html", MediaType.TEXT_HTML).mediaType("json", MediaType.APPLICATION_JSON)
				.defaultContentType(MediaType.TEXT_HTML);
	}

	
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver() {
		InternalResourceViewResolver r1 = new InternalResourceViewResolver();
		r1.setPrefix("/WEB-INF/views/");
		r1.setSuffix(".jsp");
		r1.setViewClass(JstlView.class);
		return r1;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames("classpath:messages/messages", "classpath:messages/validation");
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(SECONDS_30);
		return messageSource;
	}
}