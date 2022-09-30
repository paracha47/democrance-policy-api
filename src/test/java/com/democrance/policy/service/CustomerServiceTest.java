package com.democrance.policy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.democrance.policy.exception.ResourceNotFoundException;
import com.democrance.policy.model.Customer;
import com.democrance.policy.repository.CustomerRepository;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerService.class)
public class CustomerServiceTest {

	@Autowired
	CustomerService customerService;
	
	@MockBean
	CustomerRepository customerRepository;
	
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
		
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		
		when(customerRepository.findById(any())).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).
                thenAnswer((invocation) -> invocation.getArguments()[0]);
        when(customerRepository.findAll()).thenReturn(customers);
	}
	
	@Test
	void testCustomerFindById() {
		Customer customer = customerService.getCustomerById(100L);
		assertCustomer(customer);
	}
	
	@Test
	void testCustomerNotFind() {

		CustomerService customerService = Mockito.mock(CustomerService.class);
	    Mockito.when(customerService.getCustomerById(101L)).thenThrow(new ResourceNotFoundException("customer id not found"));

		ResourceNotFoundException exception = assertThrows(
				ResourceNotFoundException.class,
                () -> customerService.getCustomerById(101L),
                "customer id not found"
        );
        assertTrue(exception.getMessage().contains("customer id not found"));	
    }
	
	
	@Test
	void testGetAllCustomer() {
		List<Customer> customerList = customerService.findAllCustomer();
		assertThat(customerList).isNotNull();
		assertThat(customerList.size()).isEqualTo(1);
		assertCustomer(customerList.get(0));
	}
	
	
	private void assertCustomer(Customer customer) {
		assertThat(customer).isNotNull();
		assertEquals(customer.getFirstName(), "Ben");
		assertEquals(customer.getLastName(), "Stokes");
		assertEquals(customer.getEmail(), "adnan72.uol@gmail.com");
	}
}
