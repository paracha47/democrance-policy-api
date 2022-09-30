package com.democrance.policy.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.democrance.policy.exception.ResourceNotFoundException;
import com.democrance.policy.model.History;
import com.democrance.policy.model.Policy;
import com.democrance.policy.model.PolicyStateEnum;
import com.democrance.policy.repository.CustomerRepository;
import com.democrance.policy.repository.HistoryRepository;
import com.democrance.policy.repository.PolicyRepository;

@Service
public class PolicyServiceImpl implements PolicyService {

	@Autowired
	PolicyRepository policyRepository;

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	HistoryRepository historyRepository;
	
	@Override
	public Policy createPolicy(Policy requestPolicy) {
		if(requestPolicy.getCustomerId() == null) 
	        throw new ResourceNotFoundException("customer ID can not be null");
		
		if (!customerRepository.existsById(requestPolicy.getCustomerId())) 
	        throw new ResourceNotFoundException("Customer Not Found =>() " + requestPolicy.getCustomerId());
		
		policyRepository.save(requestPolicy);
		logPloicyState(requestPolicy, getMappedPolicyState(requestPolicy.getState()));
		
		return requestPolicy;
	}

	@Override
	public Policy updatePolicy(long id, Policy requestPolicy) {
		
		if (!policyRepository.existsById(id)) 
	        throw new ResourceNotFoundException("Policy id " + id + "not found");
		
		policyRepository.save(requestPolicy);
		logPloicyState(requestPolicy, getMappedPolicyState(requestPolicy.getState()));
		
		return requestPolicy;
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
	
	@Override
	public List<History> getPolicyHistory(long id) {
	    if (!policyRepository.existsById(id)) {
	        throw new ResourceNotFoundException("Not found Policy with id = " + id);
	    }
		List<History> policyHistory = historyRepository.findHistoryByPolicyId(id);
		return policyHistory;
	}
	
	private void logPloicyState(Policy policy,String state) {
		History history = History.builder()
								 .policyId(policy.getId())
								 .customerId(policy.getCustomerId())
								 .state(state)
								 .createdDate(Calendar.getInstance())
								 .build();
		historyRepository.save(history);
	}
	
	private String getMappedPolicyState(String requestState) {
		String state = requestState;
		if(requestState.equalsIgnoreCase("quoted")){
			state =  PolicyStateEnum.ACCEPTED.getParam();
		}
		return state;
	}

}
