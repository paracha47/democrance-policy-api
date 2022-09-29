package com.democrance.policy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.democrance.policy.DemocrancePolicyApiApplication;
import com.democrance.policy.model.Customer;

@SpringBootTest(classes = DemocrancePolicyApiApplication.class, 
webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerControllerIntegrationTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testAddEmployee() {
		Customer customer = new Customer(10L, "Gupta", "Stokes", "adnan72.uol@gmail.com", "Male", "25-06-1991", "JLT One Dubai");
		ResponseEntity<Customer> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/api/v1/create_customer", customer, Customer.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
	}
}
