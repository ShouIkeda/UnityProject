package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.CustomerFriend;
import com.example.repository.CustomerRepositoryHira7;

@Service
public class CustomerServiceHira7 {
	
	@Autowired
	CustomerRepositoryHira7 customerRepositry;
	
	public CustomerFriend getEmail(String email,String friendEmail) {
		CustomerFriend customerFriend = null;
		String Email = customerRepositry.getEmail(email);
		try {
		if(email.equals(Email)){//1 もしテーブルにemailが存在したら
			System.out.println(email + "あなたのアカウントが存在しました");
			try {//2 basicテーブルにフレンドがいるか確認
				String FriendEmail = customerRepositry.getFriendEmail(friendEmail);
				if(friendEmail.equals(FriendEmail)){
					 System.out.println("後半emailのアカウントが存在しました");
					try {//3 user_friendテーブルに自分とフレンドがいるか確認
						customerFriend = customerRepositry.getUserFriendEmail(email,friendEmail);
						
						//3 さらに「USER_FRIEND」テーブルにemailとfriendemailの組み合わせが存在するなら
						 if(customerFriend.getEmail().equals(email) && customerFriend.getFriendemail().equals(friendEmail) ){
							 System.out.println("あなたと後半メールの人はフレンドです");
								customerRepositry.deleteUserFriendEmail(email,friendEmail); 
								System.out.println("フレンド" + customerFriend.getFriendemail() + "のフレンド関係を削除します");
								return customerFriend;
								}else {
									System.out.println(email + ":" + friendEmail + ":" + "あなたとフレンドではありませんでした");
									}	
					}catch(NullPointerException e)
					{//3 user_friendテーブルに自分とフレンドがいなかったら
						return null ;
					}
				}else {
					System.out.println(email + "フレンドアカウントが存在しませんでした");
					}
			}catch(NullPointerException e)
			{//2 basicテーブルにフレンドがいなかったら
				return null ;
			}
		}else {
			System.out.println(email + "あなたのアカウントが存在しませんでした");
		}
		}catch(NullPointerException e)
		{	
			return null ;	
		}
		
		return customerFriend ;
	}

}
