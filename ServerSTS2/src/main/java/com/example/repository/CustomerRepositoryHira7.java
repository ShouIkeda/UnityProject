package com.example.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.CustomerFriend;

//import com.example.model.*;


@Repository
public class CustomerRepositoryHira7 {
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	

	@Transactional
	public String getEmail(String email) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("Email", email);
		String nowPoint = null;
		try {
		nowPoint = jdbcTemplate.queryForObject("SELECT Email FROM `user_basic` WHERE Email = :Email", param,
				String.class);
		return nowPoint;
		}catch(EmptyResultDataAccessException e)
		{
			System.out.println("あなたのアカウントのデータが読み込めません。");
			e.printStackTrace();
			return nowPoint;
		}
		
		

		
	}
	
	
	public String getFriendEmail(String friendEmail) {
		SqlParameterSource param = new MapSqlParameterSource().addValue("Email", friendEmail);
		String nowPoint = null;
		try {
		nowPoint = jdbcTemplate.queryForObject("SELECT Email FROM `user_basic` WHERE Email = :Email", param,
				String.class);
		return nowPoint;
		}catch(EmptyResultDataAccessException e)
		{
			System.out.println("フレンドEmailデータが読み込めません。");
			e.printStackTrace();
			return nowPoint;
		}
		
	}
	
public CustomerFriend getUserFriendEmail(String email,String friendEmail) {
		
		CustomerFriendMapper customerFrendMapper = new CustomerFriendMapper();
		
		SqlParameterSource param = new MapSqlParameterSource().addValue(
				"Email", email).addValue("friendEmail", friendEmail);
		
		CustomerFriend nowPoint = null;
		try {
		nowPoint = jdbcTemplate.queryForObject(
				"SELECT * FROM `user_friend` WHERE Email = :Email AND friendEmail = :friendEmail", param,
				customerFrendMapper);
		return nowPoint;
		}catch (EmptyResultDataAccessException e) {
			System.out.println("あなたとフレンドのデータがフレンドデータとして残ってません。");
			e.printStackTrace();

					return  nowPoint;
		}

	}
	
	/**
	 * 【呼び出されてません:参考用】<br>
	 * DBからの取り出し方を指示するMapperクラス<br>
	 * AmountCustomerテーブル専用
	 * Class:CustomerHira7
	 */	
	private class CustomerFriendMapper implements RowMapper<CustomerFriend> {
		@Override
		public CustomerFriend mapRow(ResultSet rs, int rowNum) throws SQLException {
			//i 値を取り出し
			String email = rs.getString("Email");
			String friendEmail = rs.getString("friendEmail");
			System.out.printf(email, friendEmail);
			return new CustomerFriend(email, friendEmail);
		}
	}
	
	/**
	 * 【呼び出されてません:参考用】<br>
	 * DBのID=999を1件削除する<br>
	 * 　DELETE文
	 * @return 削除行数
	 */
	@Transactional
	public CustomerFriend deleteUserFriendEmail(String email,String friendEmail) {
		
		CustomerFriendMapper customerFrendMapper = new CustomerFriendMapper();
		
		SqlParameterSource param = new MapSqlParameterSource().addValue(
				"Email", email).addValue("friendEmail", friendEmail);
		
		CustomerFriend deletedRows = null;
		try {

			deletedRows = jdbcTemplate.queryForObject(
			"SELECT * FROM `user_friend` WHERE Email = :Email AND friendEmail = :friendEmail", param,
			customerFrendMapper);
			// DELETE文を実行して、1行削除
		 int deletedRowsInt = jdbcTemplate.update("DELETE FROM `user_friend` WHERE Email = :Email AND friendEmail = :friendEmail", param);
		
		 System.out.println("deletedRowsInt=" + deletedRowsInt);
		 return deletedRows;
	}catch(EmptyResultDataAccessException e) {
		System.out.println("データが読み込めません。");
		e.printStackTrace();
		
		return null;
	}
		
	}

}
