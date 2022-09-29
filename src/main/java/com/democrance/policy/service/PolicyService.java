package com.democrance.policy.service;

import java.util.List;

import com.democrance.policy.model.Policy;

public interface PolicyService {

	Policy createPolicy(long id, Policy policy);

	Policy updatePolicy(long id, Policy policy);

	Policy getPolicy(long id);

	List<Policy> getCustomerPolicies(long custId);
}
