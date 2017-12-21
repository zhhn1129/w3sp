package com.cernet.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "W3SP_WEBLEAK_HANDLE_LAST1_VIEW")
public class webleakHandleLast1View extends BaseObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "WEBLEAK_ID")
	private String webleakId;
	
	@Column(name = "FRIDAY")
	private Date friday;    				//周五时间

	public String getWebleakId() {
		return webleakId;
	}

	public void setWebleakId(String webleakId) {
		this.webleakId = webleakId;
	}

	public Date getFriday() {
		return friday;
	}

	public void setFriday(Date friday) {
		this.friday = friday;
	}
	
}
