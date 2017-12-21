package com.cernet.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_INFO")
public class UserInfo implements Serializable{
	@Id
	@Column(name =  "ACCOUNT")
	private String account;
	@Column(name = "KEY")
	private String key;
	@Column(name = "ENC_PW")
	private String encPw;
	
	@Column(name = "DEPARTMENT")
	private String department;

	
	@Column(name = "CONTACT")
	private String contact;
	
	@Column(name = "STATUS_ID")
	private Long statusId;
	
	
	
	
	
	public Long getStatusId() {
		return statusId;
	}
	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getEncPw() {
		return encPw;
	}
	public void setEncPw(String encPw) {
		this.encPw = encPw;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}

	

}
