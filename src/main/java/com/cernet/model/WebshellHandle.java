package com.cernet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="W3SP_WEBSHELL_HANDLE")
public class WebshellHandle extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1696294137905241018L;
	
	@Id
	@Column(name="ID")
	@SequenceGenerator(name = "W3SP_WEBSHELL_HANDLE_SEQ", sequenceName = "W3SP_WEBSHELL_HANDLE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "W3SP_WEBSHELL_HANDLE_SEQ")
	private Long id;
	
	@Column(name="WEBSHELL_ID")
	private Long webshellId;
	
	@Column(name="HANDLE_DATE")
	private Date handleDate;
	
	@Column(name="ADDOPR")
	private String addopr;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name="MEMO")
	private String memo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getWebshellId() {
		return webshellId;
	}

	public void setWebshellId(Long webshellId) {
		this.webshellId = webshellId;
	}

	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}

	public String getAddopr() {
		return addopr;
	}

	public void setAddopr(String addopr) {
		this.addopr = addopr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
