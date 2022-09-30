package com.democrance.policy.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.democrance.policy.DemocrancePolicyApiApplication;
import com.democrance.policy.model.Customer;
import com.democrance.policy.model.Policy;
import com.democrance.policy.repository.CustomerRepository;
import com.democrance.policy.service.CustomerService;

@SpringBootTest(classes = DemocrancePolicyApiApplication.class, 
webEnvironment = WebEnvironment.RANDOM_PORT)
public class PolicyControllerIntegrationTest {

	String firstName = "Ben";
	String lastName = "Stokes";
	String gender = "Male";
	String email = "adnan72.uol@gmail.com";
	
	@MockBean
	CustomerRepository customerRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@BeforeEach
	void setUp() {
		
		Customer customer = Customer.builder()
                .id(100L)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .gender(gender)
                .build();
		
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		
		when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
		when(customerRepository.existsById(any())).thenReturn(true);
        when(customerRepository.save(any(Customer.class))).
                thenAnswer((invocation) -> invocation.getArguments()[0]);
        when(customerRepository.findAll()).thenReturn(customers);
	}
	
	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer(10L, "Gupta", "Stokes", "adnan72.uol@gmail.com", "Male", "25-06-1991", "JLT One Dubai");
		ResponseEntity<Customer> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/api/v1/create_customer", customer, Customer.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
	}
	
	@Test
	public void testCreatePolicy() {
		Policy policy = new Policy(10L, "personal-accident", 200L, 200000L, "active", 100L);
		ResponseEntity<Policy> responseEntity = this.restTemplate
				.postForEntity("http://localhost:" + port + "/api/v1/quote", policy, Policy.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
	}
	
	@Test
	public void testGetAllCustomer() {

		ResponseEntity<Customer> responseEntity = this.restTemplate
				.getForEntity("http://localhost:" + port + "/api/v1/customer",Customer.class);
		assertEquals(201, responseEntity.getStatusCodeValue());
	}
}
