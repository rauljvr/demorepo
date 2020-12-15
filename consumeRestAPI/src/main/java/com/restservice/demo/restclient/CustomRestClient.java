package com.restservice.demo.restclient;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restservice.demo.exceptionshandler.GenericException;
import com.restservice.demo.exceptionshandler.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomRestClient {

	private RestTemplate restTemplate;
	private ObjectMapper objectMapper;

	public CustomRestClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
		restTemplate.setErrorHandler(new ResponseErrorHandlerCoreRestCall());
		this.restTemplate = restTemplate;
		this.objectMapper = objectMapper;
	}

	public <T> T get(String uri, Class<T> responseType) {

		Map<String, Long> params = new HashMap<>();
		return this.get(uri, responseType, params);
	}

	public <T> T get(String uri, Class<T> responseType, Map<String, Long> params) {

		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class, params);
		return processResponse(uri, response, responseType);
	}

	public <S, T> T put(String uri, S data, Class<T> responseType, Map<String, Long> params) {

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<S> requestUpdate = new HttpEntity<>(data, headers);
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.PUT, requestUpdate, String.class,
				params);

		return processResponse(uri, response, responseType);
	}

	public <S, T> T post(String uri, S data, Class<T> responseType) {

		ResponseEntity<String> response = restTemplate.postForEntity(uri, data, String.class);
		return processResponse(uri, response, responseType);
	}

	public void delete(String uri, Map<String, Long> params) {

		restTemplate.delete(uri, params);
	}

	private <T> T processResponse(String uri, ResponseEntity<String> response, Class<T> responseType) {
		if (!response.getStatusCode().is2xxSuccessful()) {
			log.error("Error while calling Rest service: {} status: {}", uri, response.getStatusCode());

			switch (response.getStatusCode().value()) {
			case 404:
				throw new ResourceNotFoundException("Resource not found");
			case 500:
			default:
				throw new GenericException(response.getBody());
			}
		} else {
			log.info("Response received from {} with status {} and body {}", uri, response.getStatusCodeValue(),
					response.getBody());
			T body = null;
			try {
				body = objectMapper.readValue(response.getBody(), responseType);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
				throw new GenericException(e.getMessage());
			}
			return body;
		}
	}

	class ResponseErrorHandlerCoreRestCall implements ResponseErrorHandler {

		@Override
		public void handleError(ClientHttpResponse response) throws IOException {
			log.error("RestTemplate error: {} {}", response.getStatusCode().value(), response.getStatusText());
		}

		@Override
		public boolean hasError(ClientHttpResponse response) throws IOException {
			return (!response.getStatusCode().is2xxSuccessful());
		}
	}

}
