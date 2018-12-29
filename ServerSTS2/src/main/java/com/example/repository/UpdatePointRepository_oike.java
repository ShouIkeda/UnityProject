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

import com.example.model.Customer;

//i ポイント更新機能のリポジトリクラス
@Repository
public class UpdatePointRepository_oike {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	
	// Emailチェック
	public String checkEmail(String email) {
		//i 異常時nullで返すのでnullを代入
		String checkedEmail = null;
		
		// SQLプレースホルダーの値をセット
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		
		try {
			//i 抽出条件付きSELECT文を実行して、DB上の"Email"を取得
			checkedEmail = jdbcTemplate.queryForObject("SELECT Email FROM user_basic WHERE Email = :email", param, String.class);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("Emailが存在しません。");
			e.printStackTrace();
		}
		
		return checkedEmail;
	}
	
	// DB上のPointを書き換える
	// DB上の値に干渉するときに@Transactionalを記入（DBのデータ書き換えが完了していない場合、書き換え前に戻してくれる）
	@Transactional
	public void addPoint(String checkedEmail, int addPoint) {	
		// SQLプレースホルダーの値をセット
		SqlParameterSource param = new MapSqlParameterSource().addValue("checkedEmail", checkedEmail).addValue("addPoint", addPoint);
		
		try {
			//i 抽出条件付きUPDATE文を実行 (対象は常に1行)
			jdbcTemplate.update("UPDATE user_basic SET Point = Point + :addPoint WHERE Email = :checkedEmail", param);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("データが読み込めなかったことでPointを加算できなかった。");
			e.printStackTrace();
		}
	}
	
	//i コントローラまで返却するDB上のuser_basic全データ
	public Customer getCustomer(String checkedEmail) {
		//i 異常時nullで返すのでnullを代入
		Customer customer = null;
		// Mapperクラスの生成
		CustomerMapper customerMapper = new CustomerMapper();
		// SQLプレースホルダーの値をセット
		SqlParameterSource param = new MapSqlParameterSource().addValue("checkedEmail", checkedEmail);
		
		try {
			//i 抽出条件付きSELECT文を実行して、DBの値を取得 (対象は常に1行)
			customer = jdbcTemplate.queryForObject("SELECT * FROM user_basic WHERE Email = :checkedEmail", param,
					customerMapper);
		} catch (EmptyResultDataAccessException e) {
			System.out.println("データが読み込めません。");
			e.printStackTrace();
		}
		
		return customer;
	}
	
	// Mapperクラス
	private class CustomerMapper implements RowMapper<Customer> {
		@Override
		public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
			//i 値を取り出す
			String email = rs.getString("email");
			String password = rs.getString("password");
			String nickname = rs.getString("nickname");
			int point = rs.getInt("point");
			int rank = rs.getInt("rank");
			int age = rs.getInt("age");
			String entryDate = rs.getString("entryDate");
			String lastDateTime = rs.getString("lastDateTime");
			int loginState = rs.getInt("loginState");
			
			return new Customer(email, password, nickname, point, rank, age, entryDate, lastDateTime, loginState);
		}
	}
}
