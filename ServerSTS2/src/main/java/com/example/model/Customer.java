package com.example.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Customer {


		String emall;
		String password;
		String nickname;
		int point;
		int rank;
		int age;
		String entryDate;
		String lastDateTime;
		int loginState;
		

		public void screenString() {
			System.out.println(
					 emall + ":" +
					 password + ":" +
					 nickname + ":" +
					 point + ":" +
					 rank + ":" +
					 age + ":" +
					 entryDate + ":" +
					 lastDateTime + ":" +
					 loginState
					);
			
		}
}
