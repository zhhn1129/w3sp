/**
 * 
 */
package com.cernet.spider;


/**
 * Created by Eclipse.
 * 
 * @author Gump
 * @date 2013-10-31
 */
public class Article {
	private int id;
	private int colId; // 栏目id
	private String title; // 标题
	private String content; // 正文
	private String from; // 文章来源
	private String pubTime; // 发布时间
	private String pickTime; // 采集时间
	private String snapshot; // 文章快照
	private String srcURL; // 文章URL

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getColId() {
		return colId;
	}

	public void setColId(int colId) {
		this.colId = colId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public String getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public String getPickTime() {
		return pickTime;
	}

	public void setPickTime(String pickTime) {
		this.pickTime = pickTime;
	}

	public String getSrcURL() {
		return srcURL;
	}

	public void setSrcURL(String srcURL) {
		this.srcURL = srcURL;
	}

	
}
