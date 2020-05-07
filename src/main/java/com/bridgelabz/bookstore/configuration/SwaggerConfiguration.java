package com.bridgelabz.bookstore.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2 
@Configuration
public class SwaggerConfiguration {
	/**
	 * @author sachin viraktamath 
	 * SwaggerConfiguration contain swagger configuration
	 *         

	 *        
	 */
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.bridgelabz.bookstore")).paths(PathSelectors.any())
				.build();
	}
}