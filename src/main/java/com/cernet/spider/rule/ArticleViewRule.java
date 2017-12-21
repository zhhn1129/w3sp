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
public class ArticleViewRule extends Rule {
	private TitleRule titleRule;
	private ContentRule contentRule;
	private PubTimeRule pubTimeRule;
	private FromRule fromRule;
	public TitleRule getTitleRule() {
		return titleRule;
	}
	public void setTitleRule(TitleRule titleRule) {
		this.titleRule = titleRule;
	}
	public ContentRule getContentRule() {
		return contentRule;
	}
	public void setContentRule(ContentRule contentRule) {
		this.contentRule = contentRule;
	}
	public PubTimeRule getPubTimeRule() {
		return pubTimeRule;
	}
	public void setPubTimeRule(PubTimeRule pubTimeRule) {
		this.pubTimeRule = pubTimeRule;
	}
	public FromRule getFromRule() {
		return fromRule;
	}
	public void setFromRule(FromRule fromRule) {
		this.fromRule = fromRule;
	}
	

	
}
