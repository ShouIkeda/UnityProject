package com.example.controller;

import com.example.model.Customer;
import com.example.model.Monsters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.service.CustomerService_iga;


@RestController
public class CustomerRestController_iga {
	
	@Autowired
	CustomerService_iga customerService;
	
	//http://localhost:8080/aa@example.com/find
	@RequestMapping(value = "{email}/find", method = RequestMethod.GET)
	Customer addPoint(@PathVariable String email) {
		Customer totalValue = customerService.getCustomer(email);
		return totalValue;
	}
	
	//http://localhost:8080/aa@example.com/monster
	@RequestMapping(value = "{email}/monster", method = RequestMethod.GET)
	Monsters getPoint(@PathVariable String email) {
		Monsters totalValue = customerService.getMonsters(email);
		return totalValue;
	}
}
