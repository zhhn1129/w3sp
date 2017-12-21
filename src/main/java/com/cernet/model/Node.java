package com.cernet.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;




@Entity
@Table(name = "NODE_INFO")
public class Node extends BaseObject {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3864255228807620429L;
	
	
	@Id
	@Column(name = "NODE_ID")
	private String nodeId;


	public String getNodeId() {
		return nodeId;
	}


	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	
}
