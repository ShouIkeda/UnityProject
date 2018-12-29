package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.CustomerFriends;
import com.example.model.CustomerLoginAndLogout;
import com.example.model.CustomerSave;
import com.example.model.Shop;
import com.example.service.CustomerService_ike;

@RestController
public class CustomerRestController_ike {
	@Autowired
	CustomerService_ike customerService;

	//2新規登録メソッド
	//(2実験用。実際はリクエストがポスト)http://localhost:8080/zzz@example.com/pass/z26/26/save
	//GETの時
	//@RequestMapping(value = "{email}/{pass}/{nickname}/{age}/save",method = RequestMethod.GET)	
	//POSTの時
	@RequestMapping(value = "save",method = RequestMethod.POST)
	CustomerSave CustomerNewRegistration(
		//GET
		//@PathVariable String email,@PathVariable String pass,@PathVariable String nickname,@PathVariable int age
		//POST
		@RequestParam ("email")String email,@RequestParam ("password")String pass,
		@RequestParam ("nickname")String nickname,@RequestParam ("age")int age
		) {
			System.out.println(age);
			CustomerSave customerSave = customerService.CustomerNewRegistration(email, pass, nickname, age);
			return customerSave;
	}
	
	//3Loginメソッド
	//(3実験用。実際はリクエストがポスト)http://localhost:8080/aa@example.com/pass/login
	//GETの時
	//@RequestMapping(value = "{email}/{pass}/login", method = RequestMethod.GET)
	//POSTの時
	@RequestMapping(value = "login",method = RequestMethod.POST)
	CustomerLoginAndLogout CustomerLogin(
			//GETリクエスト
			//@PathVariable String email,@PathVariable String pass
			//POSTリクエスト
			@RequestParam ("email")String email,@RequestParam ("password")String pass
			) {
		CustomerLoginAndLogout user = customerService.CustomerLogin(email,pass);
		return user;
	}
	
	//4Logoutメソッド
	//http://localhost:8080/aa@example.com/pass/logout
	//GETリクエスト
	//@RequestMapping(value = "{email}/{pass}/logout", method = RequestMethod.GET)
	//POSTリクエスト
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	CustomerLoginAndLogout CustomerLogout(
			//GET
			//@PathVariable String email, @PathVariable String pass
			//POST
			@RequestParam ("email")String email,@RequestParam ("password")String pass
			) {
		CustomerLoginAndLogout user = customerService.CustomerLogout(email,pass);
		return user;
	}
	
	//6 フレンド1件登録
	//http://localhost:8080/cc@example.com/ee@example.com/friend
	@RequestMapping(value = "{email}/{friendemail}/friend", method = RequestMethod.GET)
	CustomerFriends friend(@PathVariable String email, @PathVariable String friendemail) {
		CustomerFriends FriendList = customerService.friend(email,friendemail);
		return FriendList;
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	//暇だからshop作った
	//http://localhost:8080/getShop
	@RequestMapping(value = "getShop", method = RequestMethod.GET)
	Shop getShop() {
		Shop shop = customerService.getShop();
		return shop;
	}	
	
	//http://localhost:8080/portion/getGoodsPrice  ←大文字小文字に注意
	@RequestMapping(value = "{goodsName}/getGoodsPrice", method = RequestMethod.GET)
	int getGoodsPrice(@PathVariable String goodsName) {
		int price = customerService.getGoodsPrice(goodsName);
		return price;
	}
	
}
