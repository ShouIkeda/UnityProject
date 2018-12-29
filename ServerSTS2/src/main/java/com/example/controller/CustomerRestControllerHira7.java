package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.CustomerFriend;
//import com.example.domain.AmountCustomer;
import com.example.service.CustomerServiceHira7;

// RestControllerはコントローラーの一種 RESTful API(REST API)ベースのコントローラー
@RestController
public class CustomerRestControllerHira7 {

	/**i コントローラーから呼び出すサービス (DIによりインスタンス化) */
	@Autowired
	CustomerServiceHira7 customerService;

	//http://localhost:8080/aa@example.com/bb@example.com/deleteFriend
	@RequestMapping(value = "{email}/{friendEmail}/deleteFriend", method = RequestMethod.GET)
	CustomerFriend getEmail(@PathVariable String email,@PathVariable String friendEmail) {
		CustomerFriend totalValue = customerService.getEmail(email,friendEmail);
		return totalValue;
	}
}
