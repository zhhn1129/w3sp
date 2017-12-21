package com.cernet.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="W3SP_WEBLEAK_VIEW")
public class WebLeakRealTimeView extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7971296472689693675L;
	
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "EVENT_DATE")
	private Date eventDate;
	
	@Column(name = "NODE_ID")
	private String nodeId;
	
	@Column(name = "NAME_C")
	private String nameC;
	
	@Column(name = "NAME_C2")
	private String nameC2;
	
	@Column(name = "CORP_SUB_NAME")
	private String corpSubName;
	
	
	@Column(name = "CTRLED_TAG")
	private String ctrledTag;
	
	@Column(name = "URL")
	private String url;
	
	@Column(name = "PATH")
	private String path;
	
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name = "MEMO")
	private String memo;

	@Column(name="LEAK_TYPE")
	private String leakType;
	
	@Column(name="MOD_TIME")
	private Date modTime;
	
	@Column(name="HANDLE_DATE")
	private Date HandleDate;
	
	@Column(name="ADDOPR")
	private String addopr;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="PROD_NAME")
	private String prodName;
	
	@Column(name="SOFT_NAME")
	private String softName;
	
	@Column(name="SCORE")
	private String score;

	
	public String getNameC2() {
		return nameC2;
	}

	public void setNameC2(String nameC2) {
		this.nameC2 = nameC2;
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

	public String getCtrledTag() {
		return ctrledTag;
	}

	public void setCtrledTag(String ctrledTag) {
		this.ctrledTag = ctrledTag;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
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

	public String getLeakType() {
		return leakType;
	}

	public void setLeakType(String leakType) {
		this.leakType = leakType;
	}

	public Date getModTime() {
		return modTime;
	}

	public void setModTime(Date modTime) {
		this.modTime = modTime;
	}

	public Date getHandleDate() {
		return HandleDate;
	}

	public void setHandleDate(Date handleDate) {
		HandleDate = handleDate;
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

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getSoftName() {
		return softName;
	}

	public void setSoftName(String softName) {
		this.softName = softName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
}
