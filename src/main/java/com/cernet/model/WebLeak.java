package com.cernet.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "W3SP_WEBLEAK_20160405") //测试库
//@Table(name = "W3SP_WEBLEAK")     //正式库
public class WebLeak extends BaseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "W3SP_WEBLEAK_SEQ", sequenceName = "W3SP_WEBLEAK_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "W3SP_WEBLEAK_SEQ")
	
	
	
	@Column(name = "ID")
	private Long id;
	
	
//	@ManyToOne( fetch = FetchType.LAZY)
//	@JoinColumn(name = "NODE_ID",insertable=false,updatable = false)
//	@JsonIgnore
//	Node node;
	
	@Column(name = "EVENT_DATE")
	private Date eventDate;
	
	@Column(name = "NODE_ID")
	private String nodeId;
	
	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	@Column(name = "NAME_C")
	private String nameC;
	
	@Column(name="NAME_C2")
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

	
	public String getNameC2() {
		return nameC2;
	}

	public void setNameC2(String nameC2) {
		this.nameC2 = nameC2;
	}

	public String getLeakType() {
		return leakType;
	}

	public void setLeakType(String leakType) {
		this.leakType = leakType;
	}

	@Column(name = "LEAK_TYPE")
	private String leakType;
	
	
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
	
	
	
	
	
	
	
	
	
	
	

	
	
	
	
}
