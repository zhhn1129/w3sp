package com.cernet.model;

import java.io.Serializable;

import javax.persistence.*;





@Entity
@Table(name = "w3sp_lov")
public class DataDict extends BaseObject implements Serializable{

	@Id
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "TABLE_NAME")
	private String tableName;

	
	@Column(name = "COLUMN_NAME")
	private String columnName;
	
	@Column(name = "LIST_NAME")
	private String listName;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "LOV")
	private String lov;
	
	@Column(name = "ACTIVE_FLG")
	private String activeFlag;
	
	
	@Column(name = "ORDER_BY")
	private Long orderBy;
	
	@Column(name = "DESC_TEXT")
	private String descText;
	
	@Column(name = "CREATE_TIME")
	private String createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLov() {
		return lov;
	}

	public void setLov(String lov) {
		this.lov = lov;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public Long getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Long orderBy) {
		this.orderBy = orderBy;
	}

	public String getDescText() {
		return descText;
	}

	public void setDescText(String descText) {
		this.descText = descText;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
	
	
	
}
