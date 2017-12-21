package com.cernet.utils.log;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cernet.model.BaseObject;

@Entity
@Table(name = "TBL_ACTIONLOG")
public class Actionlog extends BaseObject{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(name = "TBL_ACTIONLOG_SEQ", sequenceName = "TBL_ACTIONLOG_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_ACTIONLOG_SEQ")

	@Column(name =  "ID")
	private Integer id;				//id号
	@Column(name = "USERNAME")
	private String username;    	//用户名
	@Column(name = "ACTION")
	private String action;    		//记录动作类型
	
	@Column(name = "DESCRIPTION")
	private String description; 	//描术信息
	
	@Column(name = "LOCATION")
	private String location;   	   //用户的访问地址
	
	@Column(name = "TIME")
	private Date time;    		   //用户操作时间
		
	@Column(name = "USER_IP")
	private String userIp;        //用户IP地址

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getUserIp() {
		return userIp;
	}

}