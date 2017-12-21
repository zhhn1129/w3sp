package com.cernet.model;

import java.util.Date;

public class WebLeakModel extends BaseObject {

	private int id;
	
	private String corpSubName;
	
	private Long ctrledNum;
	
	private Long urlNum;
	
	private Long pathNum;
	
	private Date lastEventDate;

	private String nameC;
	
	
	
	public String getNameC() {
		return nameC;
	}

	public void setNameC(String nameC) {
		this.nameC = nameC;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCorpSubName() {
		return corpSubName;
	}

	public void setCorpSubName(String corpSubName) {
		this.corpSubName = corpSubName;
	}

	public Long getCtrledNum() {
		return ctrledNum;
	}

	public void setCtrledNum(Long ctrledNum) {
		this.ctrledNum = ctrledNum;
	}

	public Long getUrlNum() {
		return urlNum;
	}

	public void setUrlNum(Long urlNum) {
		this.urlNum = urlNum;
	}

	public Long getPathNum() {
		return pathNum;
	}

	public void setPathNum(Long pathNum) {
		this.pathNum = pathNum;
	}

	public Date getLastEventDate() {
		return lastEventDate;
	}

	public void setLastEventDate(Date lastEventDate) {
		this.lastEventDate = lastEventDate;
	}
	
	
	
}
