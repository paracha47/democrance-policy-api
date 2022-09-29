package com.democrance.policy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.democrance.policy.model.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long>{
	
	  List<Policy> findPoliciesByCustomerId(Long custId);

}
