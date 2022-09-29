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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.democrance.policy.model.Customer;
import com.democrance.policy.service.CustomerService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CustomerService customerService;
	
	@PostMapping("/create_customer")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create customer", notes = "create the new customer")
    public ResponseEntity<Customer> createGame(@RequestBody Customer customer) {
		try {			
			Customer cust = customerService.createCustomer(customer);
			logger.info("Customer created : {}", customer);
			
			return new ResponseEntity<>(cust, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

    }
	
	@RequestMapping(value = "/customer")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        return new ResponseEntity<>(customerService.findAllCustomer(), HttpStatus.FOUND);
    }
	
	@GetMapping("/customer/{id}")
    @ApiOperation(value = "Get customer by id", notes = "Get customer")
    public ResponseEntity<Customer> getPolicy(@PathVariable("id") long id ) {
		Customer customer = customerService.getCustomerById(id);
		logger.info("Customer : {}", customer);
		return new ResponseEntity<>(customer, HttpStatus.OK);
    }
}
