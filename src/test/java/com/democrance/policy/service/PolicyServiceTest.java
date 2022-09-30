package com.democrance.policy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.democrance.policy.exception.ResourceNotFoundException;
import com.democrance.policy.model.Customer;
import com.democrance.policy.model.History;
import com.democrance.policy.model.Policy;
import com.democrance.policy.repository.CustomerRepository;
import com.democrance.policy.repository.HistoryRepository;
import com.democrance.policy.repository.PolicyRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest({PolicyService.class,CustomerService.class})
@Import({CustomerServiceImpl.class})
public class PolicyServiceTest {

	@Autowired
	PolicyService policyService;
	
	@MockBean
	CustomerService customerService;
	
	@MockBean
	PolicyRepository policyRepository;
	
	@MockBean
	CustomerRepository customerRepository;
	
	@MockBean
	HistoryRepository historyRepository;
	
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
	void testPolicyFindById() {
		Policy policy = policyService.getPolicy(policy_id);
		assertPolicy(policy);
	}
	
	@Test
	void testGetCustomerPolicies() {
		List<Policy> customerPolicies = policyService.getCustomerPolicies(100L);
		assertThat(customerPolicies).isNotNull();
		assertThat(customerPolicies.size()).isEqualTo(1);
		assertPolicy(customerPolicies.get(0));
	}
	
	@Test
	void testGetPolicyHistory() {
		List<History> policyHistory = policyService.getPolicyHistory(100L);
		assertThat(policyHistory).isNotNull();
		assertThat(policyHistory.size()).isEqualTo(1);
		assertHistory(policyHistory.get(0));
	}
	
	@Test
	void testCreatePolicyCustomerIdNotFound() {

		Policy policy = Policy.builder()
				.id(100L)
				.customerId(null)
				.type(type)
				.premium(premium)
				.cover(cover)
				.state(state).build();
		when(policyRepository.findById(any())).thenReturn(Optional.of(policy));

		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class,
                () -> policyService.createPolicy(policy),
                "customer ID can not be null"
        );
        assertTrue(exception.getMessage().contains("customer ID can not be null"));	
    }
	
	@Test
	void testUpdatePolicyCustomerIdNotFound() {

		Policy policy = Policy.builder()
				.id(100L)
				.customerId(null)
				.type(type)
				.premium(premium)
				.cover(cover)
				.state(state).build();
		
		when(policyRepository.existsById(any())).thenReturn(false);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> policyService.updatePolicy(0, policy));
        verify(policyRepository, never()).save(any());	
    }
	
//	@Test
//	void testPolicyNotFound() {
//
//		when(policyRepository.findById(any())).thenReturn(null);
//		Assertions.assertThrows(NullPointerException.class, () ->policyService.getPolicy(0));
//
////		PolicyService policyService = Mockito.mock(PolicyService.class);
////	    Mockito.when(policyService.getPolicy(0)).thenThrow(new ResourceNotFoundException("Policy not found"));
//
//		ResourceNotFoundException exception = assertThrows(
//				ResourceNotFoundException.class,
//                () -> policyService.getPolicy(0),
//                "Policy not found"
//        );
//
//        assertTrue(exception.getMessage().contains("Policy not found"));	
//    }
	
	@Test
	void testGetCustomerPolicyNotFound() {

		when(customerRepository.existsById(any())).thenReturn(false);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> policyService.getCustomerPolicies(100));
        verify(policyRepository, never()).findPoliciesByCustomerId(any());	
        
    }
	
	@Test
	void testGetPolicyHistoryNotFound() {

		when(policyRepository.existsById(any())).thenReturn(false);
		Assertions.assertThrows(ResourceNotFoundException.class, () -> policyService.getPolicyHistory(100));
        verify(historyRepository, never()).findHistoryByPolicyId(any());	
        
//		PolicyService policyService = Mockito.mock(PolicyService.class);
//	    Mockito.when(policyService.getPolicyHistory(100)).thenThrow(new ResourceNotFoundException("No policy history found"));
//
//		ResourceNotFoundException exception = assertThrows(
//				ResourceNotFoundException.class,
//                () -> policyService.getPolicyHistory(100),
//                "No policy history found"
//        );
//        assertTrue(exception.getMessage().contains("No policy history found"));	
    }
	
	@Test
	void testUpdatePolicy() {

		Policy policy = Policy.builder()
				.id(100L)
				.customerId(100L)
				.type(type)
				.premium(300L)
				.cover(cover)
				.state(state).build();
		when(policyRepository.findById(any())).thenReturn(Optional.of(policy));

		Policy updatedPolicy = policyService.updatePolicy(100L, policy);
		assertThat(updatedPolicy).isNotNull();
		assertEquals(updatedPolicy.getPremium(), 300L);
    }
	
	@Test
	void testUpdatePolicyStateQuoted() {

		Policy policy = Policy.builder()
				.id(100L)
				.customerId(100L)
				.type(type)
				.premium(premium)
				.cover(cover)
				.state("accepted").build();

		Policy updatedPolicy = policyService.createPolicy(policy);
		
		when(policyRepository.findById(any())).thenReturn(Optional.of(policy));
		
		assertThat(updatedPolicy).isNotNull();
		assertEquals(updatedPolicy.getState(), "accepted");
    }
	
	private void assertHistory(History history) {
		assertThat(history).isNotNull();
		assertEquals(history.getCustomerId(), customerId);
		assertEquals(history.getPolicyId(), policy_id);		
		assertEquals(history.getState(), state);		
	}

	private void assertPolicy(Policy policy) {
		assertThat(policy).isNotNull();
		assertEquals(policy.getCustomerId(), customerId);
		assertEquals(policy.getType(), type);
		assertEquals(policy.getPremium(), premium);
		assertEquals(policy.getCover(), cover);
		assertEquals(policy.getState(), state);
	}
}
