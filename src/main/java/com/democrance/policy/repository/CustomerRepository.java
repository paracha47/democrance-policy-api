package com.democrance.policy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.democrance.policy.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{
	
}
