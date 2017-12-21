package com.cernet.util;

import java.net.MalformedURLException;
import java.net.URL;

public class JoinUrl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JoinUrl joinUrl = new JoinUrl();
		String url = joinUrl.joinUrl("http://xx.com/a2/","http://xx.com/a2/b2/");
		System.out.println(url);

	}
	
	public String joinUrl(String curl,String file){
		  URL url = null;
		  String q = "";
		  try {
		   url = new   URL(new   URL(curl),file);
		   q = url.toExternalForm();
		  } catch (MalformedURLException e) {   
		 
		  }
		  url = null;
		  if(q.indexOf("#")!=-1)q = q.replaceAll("^(.+?)#.*?$", "$1");
		  return q;
		 }

}
