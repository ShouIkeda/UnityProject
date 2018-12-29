package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.CustomerFriends;
import com.example.repository.WataRepository;

@Service
public class WataService {
	@Autowired
	WataRepository wataRepository;
		public CustomerFriends friend(String email) {	
			System.out.println("friendメソッド開始");
			CustomerFriends customerFriends = null;
			
			System.out.println("Email検索開始");
			
			if(wataRepository.emailThere(email)) {
				customerFriends = wataRepository.find(email);
			}
			return customerFriends;
	}
}