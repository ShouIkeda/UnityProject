package com.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Monsters {
	
	String [] monsters;
	String monster;
	
	public   void screenString() {
		
		for  (int i = 0;i < monsters.length;i++) {
		System.out.println(monsters[i] + ":" );
		}
	}
}
