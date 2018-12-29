package com.example.service;

import com.example.model.Customer;
import com.example.model.Monsters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.CustomerRepository_iga;

@Service
public class CustomerService_iga {
	
	@Autowired
	CustomerRepository_iga customerRepositry;
	
	
	public Customer getCustomer(String email) {
		Customer customer = customerRepositry.getCustomer(email);
		return customer;
	}
	
	public Monsters getMonsters(String email) {
		Monsters monsters = customerRepositry.getMonsters(email);
		return monsters;
	}
	
	

}
