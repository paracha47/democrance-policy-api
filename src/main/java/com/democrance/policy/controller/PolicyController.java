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
import org.springframework.web.bind.annotation.RestController;

import com.democrance.policy.model.History;
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
	
	@PostMapping("/quote")
    @ApiOperation(value = "create quote", notes = "create the new quote")
    public ResponseEntity<Policy> createPolicy(@RequestBody Policy requestQuote) {
		
		Policy createPolicy = policyService.createPolicy(requestQuote);
		logger.info("Policy created : {}", createPolicy);
		return new ResponseEntity<>(createPolicy, HttpStatus.CREATED);
    }
	
	@PutMapping("/quote/{id}")
    @ApiOperation(value = "update quote", notes = "update quote")
	public ResponseEntity<Policy> updatePolicy(@PathVariable("id") long id, @RequestBody Policy policy) {
		
		return new ResponseEntity<>(policyService.updatePolicy(id,policy), HttpStatus.OK);
	}
	
	@GetMapping("/policies/{id}")
    @ApiOperation(value = "Get policy by id", notes = "Get Policy by id")
    public ResponseEntity<Policy> getPolicy(@PathVariable("id") long id ) {
		Policy policy = policyService.getPolicy(id);
		logger.info("Policy : {}", policy);
		return new ResponseEntity<>(policy, HttpStatus.OK);
    }
	
	@GetMapping("/policies")
    @ApiOperation(value = "customer policies", notes = "Get polcies by customer id")
    public ResponseEntity<List<Policy>> getCustomerPolicies(@RequestParam("customer_id") long custId ) {
		List<Policy> customerPolicies = policyService.getCustomerPolicies(custId);
		logger.info("customer policies : {}", customerPolicies);
		return new ResponseEntity<>(customerPolicies, HttpStatus.OK);
    }
	
	@GetMapping("/policies/{id}/history")
    @ApiOperation(value = "Get policy history", notes = "Get policy history")
    public ResponseEntity<List<History>> getPolicyHistory(@PathVariable("id") long id ) {
		List<History> policyHistory = policyService.getPolicyHistory(id);
		logger.info("History : {}", policyHistory);
		return new ResponseEntity<>(policyHistory, HttpStatus.OK);
    }
	
	
}
