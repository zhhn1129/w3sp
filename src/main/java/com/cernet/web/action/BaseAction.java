package com.cernet.web.action;

import java.io.IOException;
import java.util.ArrayList; 
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.utils.log.Log;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class BaseAction extends ActionSupport {
	
	protected int page = 1;
	protected int pagesize = 10;
	protected String sort;
	protected String dir;
	protected PaginatedListHibernateImpl plist;
	protected Log actionLoger;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public PaginatedListHibernateImpl getPlist() {
		return plist;
	}

	public void setPlist(PaginatedListHibernateImpl plist) {
		this.plist = plist;
	}

	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	protected HttpSession getSession() {
		return getRequest().getSession();
	}

	/**
	 * Save the message in the session, appending if messages already exist
	 * 
	 * @param msg
	 *            the message to put in the session
	 */
	@SuppressWarnings("unchecked")
	protected void saveMessage(String msg) {
		List messages = (List) getRequest().getSession().getAttribute(
				"messages");
		if (messages == null) {
			messages = new ArrayList();
		}
		messages.add(msg);
		getRequest().getSession().setAttribute("messages", messages);
	}
	
	protected void writeResponseText(String text) {
		try {
			getResponse().setCharacterEncoding("utf-8");
			getResponse().setContentType("text/html; charset=utf-8");
			getResponse().getWriter().write(text);
			getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Log getActionLoger() {
		return actionLoger;
	}

	public void setActionLoger(Log actionLoger) {
		this.actionLoger = actionLoger;
	}

}
