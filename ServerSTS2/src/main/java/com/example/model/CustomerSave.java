package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerSave {
	String email;
	String password;
	String nickname;
	int age;
}
