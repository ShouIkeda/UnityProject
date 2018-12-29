package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerLoginAndLogout {
	String email;
	String password;
	String nickname;
	int loginState;
}
