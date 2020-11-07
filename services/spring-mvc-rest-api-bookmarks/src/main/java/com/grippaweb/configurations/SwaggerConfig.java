package com.grippaweb.configurations;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
	return new Docket(DocumentationType.SWAGGER_2).select()
		.apis(RequestHandlerSelectors.basePackage("com.grippaweb.controllers")).paths(regex("/api/.*")).build()
		.apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
	return new ApiInfoBuilder().title("Spring Mvc Rest Api")
		.description("Progetto prototipo per creare Spring Mvc Rest Api da deployare su server jboss wildfly")
		.version(getClass().getPackage().getImplementationVersion()).license("Apache License Version 2.0")
		.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
		.contact(new Contact("Luciano Grippa", "https://grippaweb.eu", "grippa.luciano@gmail.com")).build();
    }
}
