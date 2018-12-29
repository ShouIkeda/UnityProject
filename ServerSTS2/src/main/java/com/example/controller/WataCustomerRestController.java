package com.example.controller;
//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.CustomerFriends;
import com.example.service.WataService;


@RestController
public class WataCustomerRestController {
	@Autowired
	WataService  wataService ;
	//http://localhost:8080/aa@example.com/friend
	@RequestMapping(value = "{email}/friend", method = RequestMethod.GET)
	CustomerFriends  friend(@PathVariable String email) {
		CustomerFriends FriendList  = wataService. friend(email);
		return FriendList ;
	}
}
