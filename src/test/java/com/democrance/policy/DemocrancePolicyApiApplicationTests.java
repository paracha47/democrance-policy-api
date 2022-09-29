package com.democrance.policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.democrance.policy.controller.CustomerController;

@SpringBootTest
class DemocrancePolicyApiApplicationTests {


	@Autowired
	CustomerController customerController;
	
	@Test
	void contextLoads() {
		assertThat(customerController).isNotNull();

	}
}
