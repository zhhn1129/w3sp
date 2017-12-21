package com.cernet.wechat;

public class TokenMessage {
	int errcode;
	String errmsg;
	String access_token;
	int expires_in;
	
	public String getToken() {
		if (errcode == 40002 || errcode == 40001) {
			System.err.println("errmsg:"+errmsg);
			return null;
		}
		if (access_token == null) {
			return null;
		}
		System.out.println("access_token="+access_token);
		return access_token;
	}
}
