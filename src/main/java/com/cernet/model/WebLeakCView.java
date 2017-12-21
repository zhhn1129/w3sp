package com.cernet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="W3SP_WEBLEAK_CORP_VIEW")
public class WebLeakCView extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1632720767297423654L;
	
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="CORP_SUB_NAME")
	private String corpSubName;
	
	@Column(name="COUNT_HOST")
	private Long countHost;
	
	@Column(name="COUNT_URL")
	private Long countURL;
	
	@Column(name="STATUS_ERR")
	private Long statusERR;
	
	@Column(name="STATUS_RPR")
	private Long statusRPR;
	
	@Column(name="STATUS_LNK")
	private Long statusLNK;
	
	@Column(name="STATUS_CLS")
	private Long statusCLS;
	
	@Column(name="RATE")
	private String rate;

	
	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCorpSubName() {
		return corpSubName;
	}

	public void setCorpSubName(String corpSubName) {
		this.corpSubName = corpSubName;
	}

	public Long getCountHost() {
		return countHost;
	}

	public void setCountHost(Long countHost) {
		this.countHost = countHost;
	}

	public Long getCountURL() {
		return countURL;
	}

	public void setCountURL(Long countURL) {
		this.countURL = countURL;
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
}





























