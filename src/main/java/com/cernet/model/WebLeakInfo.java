package com.cernet.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
//@Table(name = "W3SP_WEBLEAK_20160405")  //测试库
@Table(name = "W3SP_WEBLEAK")               //正式库
public class WebLeakInfo extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "EVENT_DATE")
	private Date eventDate;
	
	@Column(name = "NODE_ID")
	private String nodeId;

	@Column(name = "NAME_C")
	private String nameC;
	
	@Column(name = "CORP_SUB_NAME")
	private String corpSubName;
	
	@Column(name = "CTRLED_TAG")
	private String ctrledTag;
	
	@Column(name = "IP")
	private String ip;
	
	@Column(name = "URL")
	private String url;
	
	@Column(name = "CREATE_TIME")
	private Date createTime;
	
	@Column(name = "LEAK_TYPE")
	private String leakType;
	
	@Column(name = "MOD_TIME")
	private Date modTime;
	
	@Column(name = "SCORE")
	private String score;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name="CHECK_METHOD")
	private String checkMethod;

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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCheckMethod() {
		return checkMethod;
	}

	public void setCheckMethod(String checkMethod) {
		this.checkMethod = checkMethod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
