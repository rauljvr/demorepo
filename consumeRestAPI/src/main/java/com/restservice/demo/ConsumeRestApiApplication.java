package com.restservice.demo;

import java.time.Duration;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.restservice.demo.model.CompanyEntity;
import com.restservice.demo.model.UserEntity;
import com.restservice.demo.repository.UserRepository;
import com.restservice.demo.restclient.CustomRestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class ConsumeRestApiApplication implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public XmlMapper xmlMapper() {
		return new XmlMapper();
	}

	@Bean
	public CustomRestClient restTemplate(ObjectMapper objectMapper) {
		RestTemplate restTemplate = new RestTemplateBuilder().setConnectTimeout(Duration.ofMillis(5000))
				.setReadTimeout(Duration.ofMillis(5000)).build();
		return new CustomRestClient(restTemplate, objectMapper);
	}

	public static void main(String[] args) {
		SpringApplication.run(ConsumeRestApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		log.info("Starting application, loading default data...");

		CompanyEntity company1 = CompanyEntity.builder().name("My Company").catchPhrase("Living life").bs("e-commerce")
				.build();
		CompanyEntity company2 = CompanyEntity.builder().name("Another Company").catchPhrase("Enjoying life")
				.bs("Turism").build();

		UserEntity user1 = UserEntity.builder().name("Raul").email("raul@gmail.com").username("raulusr")
				.company(company1).build();
		UserEntity user2 = UserEntity.builder().name("Javier").email("javier@gmail.com").username("javierusr")
				.company(company1).build();
		UserEntity user3 = UserEntity.builder().name("Pablo").email("pablo@gmail.com").username("pablousr")
				.company(company2).build();

		company1.setUsers(Arrays.asList(user1, user2));
		company2.setUsers(Arrays.asList(user3));

		userRepository.save(user1);
		userRepository.save(user2);
		userRepository.save(user3);

		log.info("Printing all users:");
		userRepository.findAll().forEach(usr -> log.info(usr.getName()));
	}

}
