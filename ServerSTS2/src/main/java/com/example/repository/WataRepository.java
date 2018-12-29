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

import com.example.model.CustomerFriend;
import com.example.model.CustomerFriends;

@Repository
public class WataRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	public boolean emailThere(String email) {
		System.out.println("emailThereメソッド開始");
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		try {
			jdbcTemplate.queryForObject("SELECT Email FROM user_basic WHERE Email = :email",param,String.class);
			return true;
		}catch(EmptyResultDataAccessException e) {
			System.out.println("既存データなし...自分が存在しない");
			e.printStackTrace();
			return false;
		}
	}
		
	public CustomerFriends find(String email) {
		System.out.println("ここはどーだ");
		CustomerFriends customerFriends = new CustomerFriends();
		CustomerFriendMapper customerFriendMapper = new CustomerFriendMapper();
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		try {
			customerFriends.setCustomerFriends(jdbcTemplate.query("SELECT * FROM user_friend WHERE Email = :email",
					param,customerFriendMapper));
		}catch(EmptyResultDataAccessException e) {
			System.out.println("現状insert文入れてないんだからエラー出るの当たり前");
			e.printStackTrace();
		}
        return customerFriends;
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
	}

