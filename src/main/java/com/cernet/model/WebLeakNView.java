package com.cernet.model;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

@Entity
@Table(name="W3SP_WEBLEAK_NODE_VIEW")
public class WebLeakNView extends BaseObject {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -4628595419303682049L;
	
	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="CORP_SUB_NAME")
	private String corpSubName;
	
	@Column(name="NAME_C")
	private String nameC;
	
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
	
	@Formula(value = "status_cls+status_err+status_lnk+status_rpr")
	private Long statusSum; // 合计
	
	@Column(name="NODE_TYPE")
	private String nodeType;
	
	@Transient
	private String statusPro;				//未修复漏洞占比

	
	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
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

	public String getNameC() {
		return nameC;
	}

	public void setNameC(String nameC) {
		this.nameC = nameC;
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
	public Long getStatusSum() {
		return statusSum;
	}

	public void setStatusSum(Long statusSum) {
		this.statusSum = statusSum;
	}

	public String getStatusPro() {
		String res;
		Long sum = 0L;
		double pro;
		if(statusCLS != null && statusERR != null && statusLNK != null && statusRPR != null){
			sum = statusCLS + statusERR + statusLNK + statusRPR;
			pro = Math.round((statusERR * 100.0 / sum));
			res = new DecimalFormat("0.00").format(pro);
		}else {
			res = "";
			return res;
		}
		 		
		return res+"%"; 
	}

	public void setStatusPro(String statusPro) {
		this.statusPro = statusPro;
	}	
	
}
