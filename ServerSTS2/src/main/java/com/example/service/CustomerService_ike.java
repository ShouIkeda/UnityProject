package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.CustomerFriends;
import com.example.model.CustomerLoginAndLogout;
import com.example.model.CustomerSave;
import com.example.model.Shop;
import com.example.repository.CustomerRepository_ike;

@Service
public class CustomerService_ike {
	@Autowired
	CustomerRepository_ike customerRepository;
	
	//2新規登録
	public CustomerSave CustomerNewRegistration(String email,String password,String nickname,int age) {
		System.out.println(age);
		CustomerSave customerSave = customerRepository.CustomerNewRegistration(email, password, nickname, age);
		return customerSave;
	}
	
	//3ログイン
	public CustomerLoginAndLogout CustomerLogin(String email,String pass) {
		CustomerLoginAndLogout user = customerRepository.CustomerLogin(email,pass);
		return user;
	}
	
	//4ログアウト
	public CustomerLoginAndLogout CustomerLogout(String email,String pass) {
		CustomerLoginAndLogout user = customerRepository.CustomerLogout(email,pass);
		return user;
	}
	
	//6 フレンド1件登録
	//i ３つの条件を満たしたらDBからCustomerFriendsを取得	
	//下記修正ver
	public CustomerFriends friend(String email,String friendEmail) {
		CustomerFriends customerFriends = null;
		if(customerRepository.emailThere(email) &&
		   customerRepository.emailThere(friendEmail) &&
		   !email.equals(friendEmail)) {
			customerFriends = customerRepository.meAndFriendNotThere(email, friendEmail);
			if(customerFriends == null) {
				customerFriends = customerRepository.find(email,friendEmail);
			}
		}
		return customerFriends;
	}
	
	public Shop getShop() {
		Shop shop = customerRepository.getShop();
		return shop;
	}
	
	public int getGoodsPrice(String goodsName) {
		int Price = customerRepository.getGoodsPrice(goodsName);
		return Price;
	}
}
