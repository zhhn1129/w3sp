package com.cernet.model;

import java.io.Serializable;
import java.util.Date;

public class LeakPmList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int totalNum;

	private String proName;
	
	private Date lastTime;

	private int lastTotalCount;

	private String status;

	private String runHours;

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}
	

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRunHours() {
		return runHours;
	}

	public void setRunHours(String runHours) {
		this.runHours = runHours;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getLastTotalCount() {
		return lastTotalCount;
	}

	public void setLastTotalCount(int lastTotalCount) {
		this.lastTotalCount = lastTotalCount;
	}

}
