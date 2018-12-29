package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Customer;
import com.example.service.UpdatePointService_oike;


@RestController
public class CustomerRestController_oike {
	//i コントローラから呼び出すサービス (DIによりインスタンス化)
	@Autowired
	UpdatePointService_oike updatePointService;

	//i ブラウザのGETリクエストの値に応じてDB上のPointを加算して返却
	//http://localhost:8080/aa@example.com/lv1/point
	@RequestMapping(value = "{email}/{lvNum}/point", method = RequestMethod.GET)
	Customer getCustomer(@PathVariable String email, @PathVariable String lvNum) {
		return updatePointService.addPoint(email, lvNum);
	}
}
