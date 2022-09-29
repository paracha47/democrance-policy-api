package com.democrance.policy.service;

import java.util.List;

import com.democrance.policy.model.History;
import com.democrance.policy.model.Policy;

public interface PolicyService {

	Policy createPolicy(Policy quote);

	Policy updatePolicy(long id, Policy policy);

	Policy getPolicy(long id);

	List<Policy> getCustomerPolicies(long custId);

	List<History> getPolicyHistory(long id);
}
