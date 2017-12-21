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
@Table(name = "W3SP_WEBLEAK_HIGHTPUSH")
public class HighPush {
	@Id
	@Column(name="ID")
	@SequenceGenerator(name="W3SP_WEBLEAK_HIGHTPUSH_SEQ",sequenceName="W3SP_WEBLEAK_HIGHTPUSH_SEQ",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="W3SP_WEBLEAK_HIGHTPUSH_SEQ")
	private Long Id;
	
	@Column(name="LEAK_TYPE")
	private String leakType;
	
	@Column(name="NAME_C")
	private String nameC;
	
	@Column(name="PRIORITY")
	private String priority;
	
	@Column(name="CREATE_TIME")
	private Date createTime;
	
	@Column(name="LEAK_TITLE")
	private String leakTitle;
	
	@Column(name="URL")
	private String url;
	
	@Column(name="LEAK_SOURCE")
	private String leakSource;
	
	@Column(name="PUSH")
	private Long push;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getLeakType() {
		return leakType;
	}

	public void setLeakType(String leakType) {
		this.leakType = leakType;
	}

	public String getNameC() {
		return nameC;
	}

	public void setNameC(String nameC) {
		this.nameC = nameC;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		
		this.createTime = createTime;
	}

	public String getLeakTitle() {
		return leakTitle;
	}

	public void setLeakTitle(String leakTitle) {
		this.leakTitle = leakTitle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLeakSource() {
		return leakSource;
	}

	public void setLeakSource(String leakSource) {
		this.leakSource = leakSource;
	}

	public Long getPush() {
		return push;
	}

	public void setPush(Long push) {
		this.push = push;
	}

}
