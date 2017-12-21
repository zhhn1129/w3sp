package com.cernet.web.action;

import java.io.IOException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cernet.model.Botnet;
import com.cernet.model.BotnetCorpView;
import com.cernet.model.BotnetNodeView;
import com.cernet.model.UserInfo;
import com.cernet.model.WebLeakCView;
import com.cernet.model.WebshellNodeView;
import com.cernet.model.WebshellView;
import com.cernet.service.BotnetCorpViewManager;
import com.cernet.service.BotnetManager;
import com.cernet.service.BotnetNodeViewManager;
import com.cernet.util.JsonBinder;

public class BotnetAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -418935515252353358L;

	private BotnetManager botnetManager;
	private BotnetCorpViewManager botnetCorpViewManager;
	private BotnetNodeViewManager botnetNodeViewManager;
	
	private List<BotnetCorpView> botnetList;
	
	private Botnet botnet;
	
	public String botnetList(){
		DetachedCriteria query = DetachedCriteria.forClass(BotnetCorpView.class);
		UserInfo user = (UserInfo)this.getSession().getAttribute("currentUser");
		if(!"技术开发部".equals(user.getDepartment())){
			query.add(Restrictions.eq("corpSubName", user.getDepartment()));
		}
		query.addOrder(Order.desc("sumCount"));
		botnetList = botnetCorpViewManager.getListByDetachedCriteria(query);
		return SUCCESS;
	}

	
	public void botnetList2(){
		DetachedCriteria query = DetachedCriteria.forClass(BotnetNodeView.class);
		
		String corpSubName = "";
		if(getRequest().getParameter("corpSubName") != null && !"".equals(getRequest().getParameter("corpSubName"))){
			corpSubName = getRequest().getParameter("corpSubName");
			query.add(Restrictions.eq("corpSubName", corpSubName));
		}
		query.addOrder(Order.desc("sumCount"));
		List<BotnetNodeView> list = botnetNodeViewManager.getListByDetachedCriteria(query);
		Long sumIp = 0L;
		Long sum = 0L;
		Long sumErr = 0L;
		for(int i=0;i<list.size();i++){
			sumIp += list.get(i).getCountIp();
			sum += list.get(i).getSumCount();
			sumErr += list.get(i).getStatusERR();
		}
		this.getSession().setAttribute("sumIp", sumIp);
		this.getSession().setAttribute("sum", sum);
		this.getSession().setAttribute("sumErrWB", sumErr);
		
		try {
			JsonBinder jb = JsonBinder.buildNonNullBinder();
			jb.setDateFormat("yyyy-MM-dd");
			String pageStr = jb.toJson(list);
			getResponse().getWriter().write(pageStr);
			getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void botnetList3(){
		DetachedCriteria query = DetachedCriteria.forClass(Botnet.class);
		
		String nameC = "";
		if(getRequest().getParameter("nameC") != null && !"".equals(getRequest().getParameter("nameC"))){
			nameC = getRequest().getParameter("nameC");
			query.add(Restrictions.eq("ctrledNameC", nameC));
		}
		query.addOrder(Order.desc("sumCount"));
		List<Botnet> list = botnetManager.getListByDetachedCriteria(query);
		try {
			JsonBinder jb = JsonBinder.buildNonNullBinder();
			jb.setDateFormat("yyyy-MM-dd");
			String pageStr = jb.toJson(list);
			getResponse().getWriter().write(pageStr);
			getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// get and set methods
	
	public Botnet getBotnet() {
		return botnet;
	}

	public void setBotnetCorpViewManager(BotnetCorpViewManager botnetCorpViewManager) {
		this.botnetCorpViewManager = botnetCorpViewManager;
	}


	public List<BotnetCorpView> getBotnetList() {
		return botnetList;
	}


	public void setBotnetList(List<BotnetCorpView> botnetList) {
		this.botnetList = botnetList;
	}


	public void setBotnet(Botnet botnet) {
		this.botnet = botnet;
	}

	public void setBotnetManager(BotnetManager botnetManager) {
		this.botnetManager = botnetManager;
	}


	public void setBotnetNodeViewManager(BotnetNodeViewManager botnetNodeViewManager) {
		this.botnetNodeViewManager = botnetNodeViewManager;
	}
	
}
