package com.example.repository;


import com.example.model.Customer;
import com.example.model.Monsters;


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

@Repository
public class CustomerRepository_iga {
	
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	
	@Transactional
	

	
	
	
	
	
	
	

	public Customer getCustomer(String Find) {
		Customer customer = null;
		CustomerMapper_ig customerMapper = new CustomerMapper_ig();

		SqlParameterSource param = new MapSqlParameterSource().addValue("Email", Find);
		try {
		 customer = jdbcTemplate.queryForObject("SELECT * FROM `user_basic` WHERE Email = :Email",param,customerMapper);
		 customer.screenString();
		}catch (EmptyResultDataAccessException e){
			System.out.println("データが読み込めません。");
			e.printStackTrace();
		}
		return customer;
		
	}
	
	public Monsters getMonsters(String email) {
		Monsters monsters = null;
		MonstersMapper monstersMapper = new MonstersMapper();

		SqlParameterSource param = new MapSqlParameterSource().addValue("Email", email);
		try {
			monsters = jdbcTemplate.queryForObject("SELECT Name FROM `USER_MONSTER` WHERE Email = :Email",param,monstersMapper);
			monsters.screenString();
		}catch (EmptyResultDataAccessException e){
			System.out.println("データが読み込めません。");
			e.printStackTrace();
		}
		return monsters;
		
	}
	
	
	
	
	
	
	private class CustomerMapper_ig implements RowMapper<Customer>{
		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

			String email = rs.getString("email");
			String password = rs.getString("password");
			String nickname = rs.getString("nickname");
			int point = rs.getInt("point");
			int rank =rs.getInt("rank");
			int age = rs.getInt("age");
			String entryDate = rs.getString("entryDate");
			String lastDateTime = rs.getString("lastDateTime");
			int loginState = rs.getInt("loginState");			
			return new Customer(email, password,nickname,point,rank,age,entryDate,lastDateTime,loginState);
		}
		
	}
		
		
		
		private class MonstersMapper implements RowMapper<Monsters>{
			@Override
			public Monsters mapRow(ResultSet rs, int rowNum) throws SQLException {

				String monster = rs.getString("Name");
				
				String monsters[] = monster.split(",",-1);
	
				return new Monsters(monsters,monster);
			}
		}
		
		


}
