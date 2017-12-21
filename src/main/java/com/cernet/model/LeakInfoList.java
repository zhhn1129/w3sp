/**
 * 
 */
package com.cernet.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author zhaohn
 * 2016-9-8 下午04:34:51
 */
public class LeakInfoList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String leakType;
	private String status;
	private Long runHours;
	private String statusPro;
	private String runStatus;
	private int totalNum;
	private int notFinished;
	private float percent;
	private Long statusCLS;

	private Long statusERR;

	private Long statusLNK;

	private Long statusRPR;
	private Date lastTime;//最近更新时间
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getRunHours() {
		return runHours;
	}

	public void setRunHours(Long runHours) {
		this.runHours = runHours;
	}

	public Long getStatusCLS() {
		return statusCLS;
	}

	public void setStatusCLS(Long statusCLS) {
		this.statusCLS = statusCLS;
	}

	public Long getStatusERR() {
		return statusERR;
	}

	public void setStatusERR(Long statusERR) {
		this.statusERR = statusERR;
	}

	public Long getStatusLNK() {
		return statusLNK;
	}

	public void setStatusLNK(Long statusLNK) {
		this.statusLNK = statusLNK;
	}

	public Long getStatusRPR() {
		return statusRPR;
	}

	public void setStatusRPR(Long statusRPR) {
		this.statusRPR = statusRPR;
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

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setRunStatus(String runStatus) {
		this.runStatus = runStatus;
	}

	public String getRunStatus() {
		return runStatus;
	}

	public void setLeakType(String leakType) {
		this.leakType = leakType;
	}

	public String getLeakType() {
		return leakType;
	}

	public void setNotFinished(int notFinished) {
		this.notFinished = notFinished;
	}

	public int getNotFinished() {
		return notFinished;
	}

	public void setPercent(float percent) {
		this.percent = percent;
	}

	public float getPercent() {
		return percent;
	}	
	
	
	
}
