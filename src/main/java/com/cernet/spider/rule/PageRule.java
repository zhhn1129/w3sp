/**
 * 
 */
package com.cernet.spider.rule;

/**
 * Created by Eclipse.
 *
 * @author Gump
 * @date   2013-10-31
 */
public class PageRule extends Rule {

	private String includedStrOfURL;
	private String excludedStrOfURL;
	private String signStr;
 

public String getIncludedStrOfURL() {
	return includedStrOfURL;
}

public void setIncludedStrOfURL(String includedStrOfURL) {
	this.includedStrOfURL = includedStrOfURL;
}

public String getSignStr() {
	return signStr;
}

public void setSignStr(String signStr) {
	this.signStr = signStr;
}

public String getExcludedStrOfURL() {
	return excludedStrOfURL;
}

public void setExcludedStrOfURL(String excludedStrOfURL) {
	this.excludedStrOfURL = excludedStrOfURL;
}
 
 
}
