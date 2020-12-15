package com.restservice.demo;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("DEMO-API").apiInfo(apiInfo()).select()
				.paths(getPaths()).build();
	}

	private Predicate<String> getPaths() {
		return or(regex("/accountService/.*"), regex("/accountService/account.*"));
	}

	/**
	 * Go on http://localhost:8080/swagger-ui.html or
	 * http://localhost:8080/swagger-ui.html#/account-service-controller to try the
	 * RESTful WS
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Account Service API").description("Tech demo")
				.termsOfServiceUrl("http://localhost").contact("Raúl Verastegui").license("Open source license")
				.licenseUrl("https://www.google.com").version("1.0").build();
	}

}