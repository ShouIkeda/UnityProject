package com.example.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.model.CustomerFriend;
import com.example.model.CustomerFriends;
import com.example.model.CustomerLoginAndLogout;
import com.example.model.CustomerSave;
import com.example.model.Goods;
import com.example.model.Shop;

@Repository
public class CustomerRepository_ike {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	//2ユーザー登録
	public CustomerSave CustomerNewRegistration(String email,String password,String nickname,int age) {
		CustomerSave customerSave = null;
		CustomerSaveMapper customerSaveMapper = new CustomerSaveMapper();
		//g現在時刻取得し、データベースに登録できる形にする
		Date d = new Date();
		SimpleDateFormat d1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String c1 = d1.format(d);
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email)
				.addValue("password", password).addValue("nickname", nickname).addValue("age", age)
				.addValue("point", 0).addValue("rank", 0).addValue("loginstate", 0)
				.addValue("entryDate", c1).addValue("lastDateTime", c1)
				.addValue("elf", "elf");
		
		//mまず重複確認。Emailとニックメームは重複不可
		try {
			customerSave = jdbcTemplate.queryForObject(
					"SELECT * FROM user_basic WHERE Email = :email OR NickName = :nickname"
					,param,customerSaveMapper);
			customerSave = null;
		} catch(Exception e) {
			e.printStackTrace();
			customerSave = null;
			
			try {
			//I 受け取ったデータで新しく行を追加
			jdbcTemplate.update("INSERT INTO user_basic(Email,Password,NickName,Point,Rank,Age,EntryDate,LastDateTime,loginstate) "
					+ "VALUES(:email,:password,:nickname,:point,:rank,:age,:entryDate,:lastDateTime,:loginstate)", param);
		
			//II user_monsterテーブルに新しく行を追加し、emailと初期モンスターエルフをセット
			jdbcTemplate.update("INSERT INTO user_monster(Email,Name)"
					+ "VALUES(:email,:elf)", param);
			
			//III returnするcustomerSaveインスタンスをセット
			customerSave = jdbcTemplate.queryForObject(
					"SELECT * FROM user_basic WHERE Email = :email AND NickName = :nickname "
					+ "AND password = :password AND Age = :age"
					,param,customerSaveMapper);	
			} catch(Exception er) {
				er.printStackTrace();
				customerSave = null;
			}
		}
		return customerSave;
	}
	
	//3ログイン用メソッド
	public CustomerLoginAndLogout CustomerLogin(String email,String pass) {
		CustomerLoginAndLogout customerLoginAndLogout = null;
		
		// DBからの取り出し方を指示するMapperクラス
		CustomerLoginAndLogoutMapper customerLoginAndLogoutMapper = new CustomerLoginAndLogoutMapper();
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("email", email).addValue("pass", pass).addValue("loginstate", 1);
		
		try {
			customerLoginAndLogout = jdbcTemplate.queryForObject(
					"SELECT * FROM user_basic WHERE Email = :email AND Password = :pass", param,
					customerLoginAndLogoutMapper);
			int loginState = customerLoginAndLogout.getLoginState();
			if(loginState == 0) {
				jdbcTemplate.update(
						"UPDATE user_basic SET LoginState = :loginstate WHERE Email = :email AND Password = :pass",
						param);
			}else {
				System.out.println("既にログインしています。");
			}
			
			//iもう一度ここで入力しないと、ログイン状態がリアルタイムで移行しない
			customerLoginAndLogout = jdbcTemplate.queryForObject(
					"SELECT * FROM user_basic WHERE Email = :email AND Password = :pass", param,
					customerLoginAndLogoutMapper);
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return customerLoginAndLogout;
	}
	
	
	//4ログラウト用メソッド
	public CustomerLoginAndLogout CustomerLogout(String email,String pass) {
		CustomerLoginAndLogout customerLoginAndLogout = null;
		
		// DBからの取り出し方を指示するMapperクラス
		CustomerLoginAndLogoutMapper customerLoginAndLogoutMapper = new CustomerLoginAndLogoutMapper();
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("email", email).addValue("pass", pass).addValue("loginstate", 0);
		
		try {
			customerLoginAndLogout = jdbcTemplate.queryForObject(
					"SELECT * FROM user_basic WHERE Email = :email AND Password = :pass", param,
					customerLoginAndLogoutMapper);
			int loginState = customerLoginAndLogout.getLoginState();
			if(loginState == 1) {
				jdbcTemplate.update(
						"UPDATE user_basic SET LoginState = :loginstate WHERE Email = :email AND Password = :pass",
						param);
			}else {
				System.out.println("既にログアウトしています。");
			}
			
			//もう一度ここで入力しないと、ログイン状態がリアルタイムで移行しない
			customerLoginAndLogout = jdbcTemplate.queryForObject(
					"SELECT * FROM user_basic WHERE Email = :email AND Password = :pass", param,
					customerLoginAndLogoutMapper);
			
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
		return customerLoginAndLogout;
	}
	
	//6フレンド1件登録
	//iメソッド3つ必要…（　＾ω＾）…
	//1つ目(-"-) 自分のemailがuser_basicにあるか確認 および friendemailが以下同文
	public boolean emailThere(String email) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		try {
			//user_basicにEmailがあるかないか。つまり自分が登録されたユーザーか。
			jdbcTemplate.queryForObject("SELECT Email FROM user_basic WHERE Email = :email",param,String.class);
			return true;
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//2つ目　(゜-゜) emailとfriendemailの組み合わせがuser_friendにあるか(あったら重複になるのでfalse返す。なかったらtrue)
	public CustomerFriends meAndFriendNotThere(String email,String friendEmail) {
		CustomerFriends customerFriends = new CustomerFriends();
		List<CustomerFriend> cFriends = new ArrayList<CustomerFriend>();
		CustomerFriendMapper customerFriendMapper = new CustomerFriendMapper();
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("friendemail", friendEmail);
		try {
			//↓そもそもフレンド一人もいなかったら、tryでexceptionエラーはくわ。で、それも正しい(true)なんだな
			jdbcTemplate.queryForObject(
					"SELECT * FROM user_friend WHERE Email = :email AND FriendEmail = :friendemail"
					, param,customerFriendMapper);
			//ここでエラーを吐かない　＝　組み合わせが存在するということ。
			//返すのはつまり空要素である。
			customerFriends.setCustomerFriends(cFriends);
			return customerFriends;
		}catch(EmptyResultDataAccessException e) {
			//くみあわせが存在しないから、find実行へ移る。
			e.printStackTrace();
			return null;
		}
	}
	
	//3つ目 実際のフレンド登録作業はこちら
	public CustomerFriends find(String email,String friendEmail) {
		CustomerFriends customerFriends = new CustomerFriends();
		CustomerFriendMapper customerFriendMapper = new CustomerFriendMapper();
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("friendemail", friendEmail);
		try {
			//1まずInsert文で更新
			jdbcTemplate.update("INSERT INTO user_friend(Email,Friendemail) VALUES(:email,:friendemail)", param);
			
			customerFriends.setCustomerFriends(jdbcTemplate.query("SELECT * FROM user_friend WHERE Email = :email",
					param,customerFriendMapper));
		}catch(EmptyResultDataAccessException e) {
			e.printStackTrace();
		}
        return customerFriends;
    }
	
	
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	//Another 暇だからshopでも作る
	//1　Shopを返す
	public Shop getShop() {
		Shop shop = new Shop();
		GoodsMapper goodsMapper = new GoodsMapper();
		SqlParameterSource param = new MapSqlParameterSource();
		try {
			shop.setGoods(jdbcTemplate.query("SELECT * FROM shop",param,goodsMapper));			
		}catch(Exception e) {
			e.printStackTrace();
			shop = null;
		}
		return shop;
	}
	
	//2　与えられたgoodsNameのpriceを返す
	public int getGoodsPrice(String goodsName) {
		int price = 0;
		Goods goods;
		GoodsMapper goodsMapper = new GoodsMapper();
		SqlParameterSource param = new MapSqlParameterSource().addValue("goodsName", goodsName);
		
		try {
			goods = jdbcTemplate.queryForObject(
					"SELECT * FROM shop WHERE GoodsName = :goodsName", param,goodsMapper);
			price = goods.getPrice();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return price;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//Mapperクラス CustomerLoginAndLogout
	private class CustomerLoginAndLogoutMapper implements RowMapper<CustomerLoginAndLogout> {
		@Override
		public CustomerLoginAndLogout mapRow(ResultSet rs, int rowNum) throws SQLException {
			// 値を取り出し
			System.out.println(rowNum);
			String email = rs.getString("email");
			String password = rs.getString("password");
			String nickname = rs.getString("nickname");
			int loginState = rs.getInt("loginstate");
			return new CustomerLoginAndLogout(email, password,nickname,loginState);
		}
	}
	
	//Mapperクラス CusutomerSave
	private class CustomerSaveMapper implements RowMapper<CustomerSave> {
		@Override
		public CustomerSave mapRow(ResultSet rs, int rowNum) throws SQLException {
			// 値を取り出し
			System.out.println(rowNum);
			String email = rs.getString("Email");
			String password = rs.getString("Password");
			String nickname = rs.getString("Nickname");
			int age = rs.getInt("Age");
			return new CustomerSave(email, password,nickname,age);
		}
	}
	
	//Mapperクラス CustomerFriend
	private class CustomerFriendMapper implements RowMapper<CustomerFriend> {
		@Override
		public CustomerFriend mapRow(ResultSet rs, int rowNum) throws SQLException {
			// i    値を取り出し
			System.out.println(rowNum);
			String email = rs.getString("Email");
			String friendEmail = rs.getString("FriendEmail");
			return new CustomerFriend(email,friendEmail);
		}
	}	
	//Mapperクラス CustomerFriend
	private class GoodsMapper implements RowMapper<Goods> {
		@Override
		public Goods mapRow(ResultSet rs, int rowNum) throws SQLException {
			// i    値を取り出し
			String goods = rs.getString("goodsName");
			int price = rs.getInt("price");
			return new Goods(goods,price);
		}
	}	
}
