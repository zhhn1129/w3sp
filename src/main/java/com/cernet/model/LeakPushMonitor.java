package com.cernet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "WEBLEAK_PUSH_MONITOR")
public class LeakPushMonitor {
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="WEBLEAK_PUSH_MONITOR_SEQ",sequenceName="WEBLEAK_PUSH_MONITOR_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="WEBLEAK_PUSH_MONITOR_SEQ")
	private int Id;
	@Column(name="PRONAME")
	private String proName;
	@Column(name="LEAK_DETAIL")
	private String leakDetail;
	@Column(name="LEAK_TYPE")
	private String leakType;
	@Column(name="LAST_TIME")
	private Date lastTime;
	@Column(name="CORP_NAME")
	private String corpName;
	@Column(name="NAME_C")
	private String nameC;
	@Column(name="STATUS")
	private String status;
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getProName() {
		return proName;
	}
	public void setProName(String proName) {
		this.proName = proName;
	}
	public String getLeakDetail() {
		return leakDetail;
	}
	public void setLeakDetail(String leakDetail) {
		this.leakDetail = leakDetail;
	}
	public String getLeakType() {
		return leakType;
	}
	public void setLeakType(String leakType) {
		this.leakType = leakType;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getCorpName() {
		return corpName;
	}
	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNameC() {
		return nameC;
	}
	public void setNameC(String nameC) {
		this.nameC = nameC;
	}

	
}
