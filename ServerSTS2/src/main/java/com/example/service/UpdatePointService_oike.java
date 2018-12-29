package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.repository.UpdatePointRepository_oike;

//i コントローラから呼び出されるサービス
@Service
public class UpdatePointService_oike {
	@Autowired
	//i リポジトリにアクセスするための変数
	UpdatePointRepository_oike updatePointRepository;
	//i 現在のポイントを格納
	Customer customer;
	//i Emailをチェックした後に決定する加えるPoint
	int addPoint;
	
	// DB上のPointに加算した値を返す（実際に返っているのはDB上のuser_basic全データ）
	public Customer addPoint(String email, String lvNum) {
		//i 送られてきたEmailをリポジトリに送ってチェックしてもらう
		//i チェックしたemailを代入
		String checkedEmail =  updatePointRepository.checkEmail(email);
		
		if(checkedEmail == null) {
			System.out.println("Emailエラー null");
		}
		
		// Unity上で押したボタンの内容で分岐
		if(lvNum.equals("lv1")) {
			addPoint = 100;
			updatePointRepository.addPoint(checkedEmail, addPoint);
		}
		else if(lvNum.equals("lv2")) {
			addPoint = 200;
			updatePointRepository.addPoint(checkedEmail, addPoint);
		}
		else if(lvNum.equals("lv3")) {
			addPoint = 300;
			updatePointRepository.addPoint(checkedEmail, addPoint);
		}
		
		//i リポジトリのgetCustomerメソッドの値を取得
		customer = updatePointRepository.getCustomer(checkedEmail);

		return customer;
	}
}
