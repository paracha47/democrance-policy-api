package com.democrance.policy.service;

import java.util.List;

import com.democrance.policy.model.Customer;

public interface CustomerService {

	Customer createCustomer(Customer customer);

	List<Customer> findAllCustomer();

	Customer getCustomerById(long id);
}
