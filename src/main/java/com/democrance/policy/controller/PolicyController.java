package com.democrance.policy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.democrance.policy.model.Policy;
import com.democrance.policy.service.PolicyService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class PolicyController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	PolicyService policyService;
	
	@PostMapping("customer/{id}/quote")
    @ApiOperation(value = "create policy", notes = "create the new Policy")
    public ResponseEntity<Policy> createPolicy(@PathVariable("id") long id, @RequestBody Policy policy) {
		
		Policy createPolicy = policyService.createPolicy(id,policy);
		logger.info("Policy created : {}", createPolicy);
		return new ResponseEntity<>(createPolicy, HttpStatus.CREATED);
    }
	
	@PutMapping("/quote/{id}")
	public ResponseEntity<Policy> updatePolicy(@PathVariable("id") long id, @RequestBody Policy policy) {
		
		return new ResponseEntity<>(policyService.updatePolicy(id,policy), HttpStatus.OK);
	}
	
	@GetMapping("/policies/{id}")
    @ApiOperation(value = "Get policy", notes = "Get Policy")
    public ResponseEntity<Policy> getPolicy(@PathVariable("id") long id ) {
		Policy policy = policyService.getPolicy(id);
		logger.info("Policy : {}", policy);
		return new ResponseEntity<>(policy, HttpStatus.CREATED);
    }
	
	@GetMapping("/policies")
    @ApiOperation(value = "customer policies", notes = "Get customer polcies")
    public ResponseEntity<List<Policy>> getCustomerPolicies(@RequestParam("customer_id") long custId ) {
		List<Policy> customerPolicies = policyService.getCustomerPolicies(custId);
		logger.info("Policy : {}", customerPolicies);
		return new ResponseEntity<>(customerPolicies, HttpStatus.CREATED);
    }
	
	
}
