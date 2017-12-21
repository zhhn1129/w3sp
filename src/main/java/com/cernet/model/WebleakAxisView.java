package com.cernet.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "W3SP_RPT_WEBLEAK_AXIS_VIEW ")
public class WebleakAxisView extends BaseObject{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID")
	private String id;    				
	
	@Column(name = "NODE_ID")
	private String nodeId;    				//学校编号
	
	@Column(name = "NAME_C")
	private String nameC;    				//学校
	
	@Column(name = "CORP_SUB_NAME")
	private String corpSubName;    			//分公司
	
	@Column(name = "URL")
	private String url;    					//url

	@Column(name = "EVENT_DATE")
	private Date eventDate;    			//事件时间
	
	@Column(name = "LEAK_TYPE")
	private String leakType;    			//事件时间
	
	@Transient
	private int axisCount;				
	
	@SuppressWarnings("unused")
	@Transient
	private String eventDateToString;
	
	public void setEventDateToString(String eventDateToString) {
		this.eventDateToString = eventDateToString;
	}
	
	public String getEventDateToString() {

		return new SimpleDateFormat("yyyy-MM-dd").format(eventDate);
	}
		
	
	@Transient
	private String weeks;    			
	
	@Column(name = "STATUS1")
	private String status1; 				
	
	@Column(name = "STATUS2")
	private String status2;   	  	 	
	
	@Column(name = "STATUS3")
	private String status3;    			
		
	@Column(name = "STATUS4")
	private String status4;      			
	
	@Column(name = "STATUS5")
	private String status5; 				
	
	@Column(name = "STATUS6")
	private String status6;   	  	 	
	
	@Column(name = "STATUS7")
	private String status7;    			
		
	@Column(name = "STATUS8")
	private String status8;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public String getStatus3() {
		return status3;
	}

	public void setStatus3(String status3) {
		this.status3 = status3;
	}

	public String getStatus4() {
		return status4;
	}

	public void setStatus4(String status4) {
		this.status4 = status4;
	}

	public String getStatus5() {
		return status5;
	}

	public void setStatus5(String status5) {
		this.status5 = status5;
	}

	public String getStatus6() {
		return status6;
	}

	public void setStatus6(String status6) {
		this.status6 = status6;
	}

	public String getStatus7() {
		return status7;
	}

	public void setStatus7(String status7) {
		this.status7 = status7;
	}

	public String getStatus8() {
		return status8;
	}

	public void setStatus8(String status8) {
		this.status8 = status8;
	}

 

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

 
	public int getAxisCount() {
		return axisCount;
	}

	public void setAxisCount(int axisCount) {
		this.axisCount = axisCount;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getLeakType() {
		return leakType;
	}

	public void setLeakType(String leakType) {
		this.leakType = leakType;
	}
	
}