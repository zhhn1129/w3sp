/**
 * 
 */
package com.cernet.spider.rule;

import java.util.List;

/**
 * Created by Eclipse.
 * 
 * @author Gump
 * @date 2013-10-31
 */
public abstract class Rule {
	protected String startStr;
	protected String endStr;
	protected String prefix;
	protected String suffix;
	protected List <String> filtedStrs;

	public String getStartStr() {
		return startStr;
	}

	public void setStartStr(String startStr) {
		this.startStr = startStr;
	}

	public String getEndStr() {
		return endStr;
	}

	public void setEndStr(String endStr) {
		this.endStr = endStr;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public List<String> getFiltedStrs() {
		return filtedStrs;
	}

	public void setFiltedStrs(List<String> filtedStrs) {
		this.filtedStrs = filtedStrs;
	}

	
}
