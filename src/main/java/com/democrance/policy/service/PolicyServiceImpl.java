package com.democrance.policy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.democrance.policy.exception.ResourceNotFoundException;
import com.democrance.policy.model.Customer;
import com.democrance.policy.model.Policy;
import com.democrance.policy.repository.CustomerRepository;
import com.democrance.policy.repository.PolicyRepository;

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	PolicyRepository policyRepository;

	@Autowired
	CustomerRepository customerRepository;
	
	@Override
	public Policy createPolicy(long custId, Policy requestPolicy) {
        Customer customer = customerRepository.findById(custId)
		        .orElseThrow(() -> new ResourceNotFoundException("Customer Not Found =>() " + custId));
        requestPolicy.setCustomer(customer);
		policyRepository.save(requestPolicy);
		return requestPolicy;
	}

	@Override
	public Policy updatePolicy(long id, Policy requestPolicy) {
		Policy policy = policyRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Policy id " + id + "not found"));
		policy.setId(id);
		return policyRepository.save(requestPolicy);
	}

	@Override
	public Policy getPolicy(long id) {
		Policy policy = policyRepository.findById(id)
		        .orElseThrow(() -> new ResourceNotFoundException("Policy id " + id + "not found"));
		return policy;
	}

	@Override
	public List<Policy> getCustomerPolicies(long custId) {
	    if (!customerRepository.existsById(custId)) {
	        throw new ResourceNotFoundException("Not found Customer with id = " + custId);
	    }
		List<Policy> customerPolicies = policyRepository.findPoliciesByCustomerId(custId);	
		return customerPolicies;
	}

}
