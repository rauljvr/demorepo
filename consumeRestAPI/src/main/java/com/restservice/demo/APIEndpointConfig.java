package com.restservice.demo;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "placeholder.endpoint")
@Getter
@Setter
public class APIEndpointConfig {

    @NotNull
	private String albumsPath;
    
	@NotNull
	private String albumPath;

	@NotNull
	private String photosPath;

	@NotNull
	private String photoPath;

}
