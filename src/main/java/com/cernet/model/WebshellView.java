package com.cernet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="W3SP_WEBSHELL_VIEW")
public class WebshellView extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8347936883019661534L;
	
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
	
	@Column(name="CTRLED_Ip")
	private String ctrledIp;
	
	@Column(name="DOMAIN")
	private String domain;
	
	@Column(name="URL")
	private String url;
	
	@Column(name="PASSWD")
	private String passwd;
	
	@Column(name="LASTTIME")
	private Date lastTime;
	
	@Column(name="SUM_COUNT")
	private Long sumCount;
	
	@Column(name="DAY_COUNT")
	private Long dayCount;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="STATUS")
	private String status;

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Long getSumCount() {
		return sumCount;
	}

	public void setSumCount(Long sumCount) {
		this.sumCount = sumCount;
	}

	public Long getDayCount() {
		return dayCount;
	}

	public void setDayCount(Long dayCount) {
		this.dayCount = dayCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}





















