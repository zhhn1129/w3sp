package com.cernet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="W3SP_WEBSHELL")
public class Webshell extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1517368097175928637L;
	
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="EVENT_DATE")
	private Date eventDate;
	
	@Column(name="NODE_ID")
	private String nodeId;
	
	@Column(name="NAME_C")
	private String nameC;
	
	@Column(name="CORP_SUB_NAME")
	private String corpSubName;
	
	@Column(name="CTRLED_IP")
	private String ctrledIp;
	
	@Column(name="DOMAIN")
	private String domain;
	
	@Column(name="URL")
	private String url;
	
	@Column(name="MEMO")
	private String memo;
	
	@Column(name="CREATE_TIME")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getNameC() {
		return nameC;
	}

	public void setNameC(String nameC) {
		this.nameC = nameC;
	}

	public String getCorpSubName() {
		return corpSubName;
	}

	public void setCorpSubName(String corpSubName) {
		this.corpSubName = corpSubName;
	}

	public String getCtrledIp() {
		return ctrledIp;
	}

	public void setCtrledIp(String ctrledIp) {
		this.ctrledIp = ctrledIp;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
































