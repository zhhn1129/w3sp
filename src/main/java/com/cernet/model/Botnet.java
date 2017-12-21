package com.cernet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="W3SP_BOTNET_VIEW")
public class Botnet extends BaseObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -889308161474301456L;

	@Id
	@Column(name="ID")
	private Long id;
	
	@Column(name="CTRLED_IP")
	private String ctrledIp;
	
	@Column(name="CTRLED_NODE_ID")
	private String ctrledNodeId;
	
	@Column(name="CTRLED_NAME_C")
	private String ctrledNameC;
	
	@Column(name="NODE_TYPE")
	private String nodeType;
	
	@Column(name="CORP_SUB_NAME")
	private String corpSubName;
	
	@Column(name="EVENT")
	private String event;
	
	@Column(name="START_TIME")
	private Date startTime;
	
	@Column(name="END_TIME")
	private Date endTime;
	
	@Column(name="CTRL_IP")
	private String ctrlIp;
	
	@Column(name="CTRL_NAME_C")
	private String ctrlNameC;
	
	@Column(name="CTRL_PORT")
	private String ctrlPort;
	
	@Column(name="SUM_COUNT")
	private Long sumCount;
	
	@Column(name="DAY_COUNT")
	private Long dayCount;
	
	@Column(name="HANDLE_DATE")
	private Date handleDate;
	
	@Column(name="ADDOPR")
	private String addopr;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATE_TIME")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCtrledIp() {
		return ctrledIp;
	}

	public void setCtrledIp(String ctrledIp) {
		this.ctrledIp = ctrledIp;
	}

	public String getCtrledNodeId() {
		return ctrledNodeId;
	}

	public void setCtrledNodeId(String ctrledNodeId) {
		this.ctrledNodeId = ctrledNodeId;
	}

	public String getCtrledNameC() {
		return ctrledNameC;
	}

	public void setCtrledNameC(String ctrledNameC) {
		this.ctrledNameC = ctrledNameC;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	
	public String getCorpSubName() {
		return corpSubName;
	}

	public void setCorpSubName(String corpSubName) {
		this.corpSubName = corpSubName;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCtrlIp() {
		return ctrlIp;
	}

	public void setCtrlIp(String ctrlIp) {
		this.ctrlIp = ctrlIp;
	}

	public String getCtrlNameC() {
		return ctrlNameC;
	}

	public void setCtrlNameC(String ctrlNameC) {
		this.ctrlNameC = ctrlNameC;
	}

	public String getCtrlPort() {
		return ctrlPort;
	}

	public void setCtrlPort(String ctrlPort) {
		this.ctrlPort = ctrlPort;
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

	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	

}
















