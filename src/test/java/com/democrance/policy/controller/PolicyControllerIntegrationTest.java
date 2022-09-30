package com.democrance.policy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.democrance.policy.model.Customer;
import com.democrance.policy.model.History;
import com.democrance.policy.model.Policy;
import com.democrance.policy.repository.CustomerRepository;
import com.democrance.policy.repository.HistoryRepository;
import com.democrance.policy.repository.PolicyRepository;
import com.democrance.policy.service.CustomerServiceImpl;
import com.democrance.policy.service.PolicyServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

//@SpringBootTest(classes = DemocrancePolicyApiApplication.class, 
//webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@WebMvcTest({CustomerController.class,PolicyController.class})
@Import({CustomerServiceImpl.class,PolicyServiceImpl.class})
public class PolicyControllerIntegrationTest {

	@MockBean
	CustomerRepository customerRepository;
	
	@MockBean
	PolicyRepository policyRepository;
	
	@MockBean
	HistoryRepository historyRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	Long policy_id = 100L;
	Long customerId = 100L;
	String type = "personal-accident";
	Long premium = 200L;
	Long cover = 200000L;
	String state = "new";
	String firstName = "Ben";
	String lastName = "Stokes";
	String gender = "Male";
	String email = "adnan72.uol@gmail.com";
	
	@BeforeEach
	void setUp() {
		
		Customer customer = Customer.builder()
                .id(100L)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .gender(gender)
                .build();
		
		Policy policy = Policy.builder()
				.id(100L)
				.customerId(customerId)
				.type(type)
				.premium(premium)
				.cover(cover)
				.state(state).build();
		
		History history = History.builder()
				 .policyId(policy_id)
				 .customerId(customerId)
				 .state(state)
				 .createdDate(Calendar.getInstance())
				 .build();
		
		List<Policy> policies = new ArrayList<Policy>();
		policies.add(policy);
		
		List<History> policyHistoryList = new ArrayList<History>();
		policyHistoryList.add(history);
		
		when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
		when(customerRepository.existsById(any())).thenReturn(true);
        when(customerRepository.save(any(Customer.class))).thenAnswer((invocation) -> invocation.getArguments()[0]);
		
		when(policyRepository.findById(any())).thenReturn(Optional.of(policy));
		when(policyRepository.existsById(any())).thenReturn(true);
		when(policyRepository.findPoliciesByCustomerId(any())).thenReturn(policies);
		when(policyRepository.save(any(Policy.class))).thenAnswer((invocation) -> invocation.getArguments()[0]);
		
		when(historyRepository.findById(any())).thenReturn(Optional.of(history));
		when(historyRepository.findHistoryByPolicyId(any())).thenReturn(policyHistoryList);
		when(historyRepository.save(any(History.class))).thenAnswer((invocation) -> invocation.getArguments()[0]);
	}
	
	@Test
	public void testCreateCustomer() throws Exception {
		Customer customer = new Customer(10L, "Gupta", "Stokes", "adnan72.uol@gmail.com", "Male", "25-06-1991", "JLT One Dubai");
        this.mockMvc.perform(post("/api/v1/create_customer")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customer)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
	}
	
	@Test
	public void testCreatePolicy() throws Exception {
		Policy policy = new Policy(10L, "personal-accident", 200L, 200000L, "active", 100L);
		this.mockMvc.perform(post("/api/v1/quote")    
				.contentType(MediaType.APPLICATION_JSON)
		        .content(asJsonString(policy)))
        		.andExpect(status().is2xxSuccessful())
        		.andReturn();
	}
	
	@Test
	public void testGetAllCustomer() throws Exception {
		this.mockMvc.perform(get("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
	}
	
	@Test
	public void testGetPolicy() throws Exception {
		this.mockMvc.perform(get("/api/v1/policies/100")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
		
	}
	
	@Test
	public void testGetPolicyHistory() throws Exception {
		this.mockMvc.perform(get("/api/v1/policies/100/history")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
		
	}
	
	
	@Test
	public void testGetCustomerById() throws Exception {
		this.mockMvc.perform(get("/api/v1/customer/100")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
	}
	
	@Test
	public void testUpdatePolicy() throws Exception {
		Policy policy = new Policy(10L, "personal-accident", 200L, 200000L, "active", 100L);
		this.mockMvc.perform(put("/api/v1/quote/10")    
				.contentType(MediaType.APPLICATION_JSON)
		        .content(asJsonString(policy)))
        		.andExpect(status().is2xxSuccessful())
        		.andReturn();
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
