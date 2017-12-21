package com.cernet.util;

public class Demo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String key = PasswordUtil.getKey(16);
		
		String password = "w3spadmin";
		
		password = PasswordUtil.encrypt(password, key);
		
		System.out.println(key+">>>>>"+password);
	}

}
