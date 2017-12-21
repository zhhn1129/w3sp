package com.cernet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="W3SP_WEBSHELL_NODE_VIEW")
public class WebshellNodeView extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3328716670247857686L;
	
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME_C")
	private String nameC;
	
	@Column(name="CORP_SUB_NAME")
	private String corpSubName;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="COUNT_IP")
	private Long countIp;
	
	@Column(name="COUNT_URL")
	private Long countUrl;
	
	@Column(name="SUM_COUNT")
	private Long sumCount;

	@Column(name="LASTTIME")
	private Date LastTime;
	
	@Column(name="STATUS_ERR")
	private Long statusERR;
	
	@Column(name="STATUS_RPR")
	private Long statusRPR;
	
	@Column(name="STATUS_LNK")
	private Long statusLNK;
	
	@Column(name="STATUS_CLS")
	private Long statusCLS;
	
	@Column(name="NODE_TYPE")
	private String nodeType;
	
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public Long getStatusERR() {
		return statusERR;
	}

	public void setStatusERR(Long statusERR) {
		this.statusERR = statusERR;
	}

	public Long getStatusRPR() {
		return statusRPR;
	}

	public void setStatusRPR(Long statusRPR) {
		this.statusRPR = statusRPR;
	}

	public Long getStatusLNK() {
		return statusLNK;
	}

	public void setStatusLNK(Long statusLNK) {
		this.statusLNK = statusLNK;
	}

	public Long getStatusCLS() {
		return statusCLS;
	}

	public void setStatusCLS(Long statusCLS) {
		this.statusCLS = statusCLS;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getSumCount() {
		return sumCount;
	}

	public void setSumCount(Long sumCount) {
		this.sumCount = sumCount;
	}

	public Long getCountIp() {
		return countIp;
	}

	public void setCountIp(Long countIp) {
		this.countIp = countIp;
	}

	public Long getCountUrl() {
		return countUrl;
	}

	public void setCountUrl(Long countUrl) {
		this.countUrl = countUrl;
	}

	public Date getLastTime() {
		return LastTime;
	}

	public void setLastTime(Date lastTime) {
		LastTime = lastTime;
	}

	

	
}
