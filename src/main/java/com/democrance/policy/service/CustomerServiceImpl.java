package com.democrance.policy.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.democrance.policy.model.Customer;
import com.democrance.policy.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Customer createCustomer(Customer customer) {
		customerRepository.save(customer);
		return customer;
	}

	@Override
	public List<Customer> findAllCustomer() {
		logger.info("---- call get all Customer -> ()");
        return this.customerRepository.findAll();
	}

	

}
