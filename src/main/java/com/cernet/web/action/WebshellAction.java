package com.cernet.web.action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cernet.model.UserInfo;
import com.cernet.model.WebLeakCView;
import com.cernet.model.WebLeakHandle;
import com.cernet.model.WebLeakNView;
import com.cernet.model.Webshell;
import com.cernet.model.WebshellCorpView;
import com.cernet.model.WebshellHandle;
import com.cernet.model.WebshellNodeView;
import com.cernet.model.WebshellView;
import com.cernet.service.DataDictManager;
import com.cernet.service.WebshellCorpViewManager;
import com.cernet.service.WebshellHandleManager;
import com.cernet.service.WebshellManager;
import com.cernet.service.WebshellNodeViewManager;
import com.cernet.service.WebshellViewManager;
import com.cernet.util.JsonBinder;

public class WebshellAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1793191300154948283L;
	
	private WebshellCorpViewManager webshellCorpViewManager; 
	private List<WebshellCorpView> webshellList; 
	private WebshellNodeViewManager webshellNodeViewManager;
	private WebshellViewManager webshellViewManager;
	private Webshell webshell;
	private WebshellManager webshellManager;
	private WebshellHandleManager webshellHandleManager;
	private List<WebshellHandle> wshList;
	
	private DataDictManager dataDictManager;
	private List handleStatusList;
	private List subCorpList;
	private Long wshId;
	
	public String webshellList(){
		UserInfo user = (UserInfo)this.getSession().getAttribute("currentUser");
		DetachedCriteria query = DetachedCriteria.forClass(WebshellCorpView.class);
		query.addOrder(Order.desc("sumCount"));
		if(!"技术开发部".equals(user.getDepartment())){
			query.add(Restrictions.eq("corpSubName", user.getDepartment()));
		}
		webshellList = webshellCorpViewManager.getListByDetachedCriteria(query);
		return SUCCESS;
	}
	
	
	public void webshellList2(){
			DetachedCriteria query = DetachedCriteria.forClass(WebshellNodeView.class);
			
			String corpSubName = "";
			if(getRequest().getParameter("corpSubName") != null && !"".equals(getRequest().getParameter("corpSubName"))){
				corpSubName = getRequest().getParameter("corpSubName");
				query.add(Restrictions.eq("corpSubName", corpSubName));
			}
			query.addOrder(Order.desc("sumCount"));
			List<WebshellNodeView> list = webshellNodeViewManager.getListByDetachedCriteria(query);
			Long sumIpWS = 0L;
			Long sumUrlWS = 0L;
			Long sumWS = 0L;
			Long sumErrWS = 0L;
			for(int i=0;i<list.size();i++){
				sumIpWS += list.get(i).getCountIp();
				sumUrlWS += list.get(i).getCountUrl();
				sumWS += list.get(i).getSumCount();
				sumErrWS += list.get(i).getStatusERR();
			}
			this.getSession().setAttribute("sumIpWS", sumIpWS);
			this.getSession().setAttribute("sumUrlWS", sumUrlWS);
			this.getSession().setAttribute("sumWS", sumWS);
			this.getSession().setAttribute("sumErrWS", sumErrWS);
			
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
	
	public void webshellList3(){
		DetachedCriteria query = DetachedCriteria.forClass(WebshellView.class);
		
		String nameC = "";
		if(getRequest().getParameter("nameC") != null && !"".equals(getRequest().getParameter("nameC"))){
			nameC = getRequest().getParameter("nameC");
			query.add(Restrictions.eq("nameC", nameC));
		}
		query.addOrder(Order.desc("sumCount"));
		List<WebshellView> list = webshellViewManager.getListByDetachedCriteria(query);
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
	
	
	public String editWebshell(){
		String url=this.getRequest().getParameter("url");
		DetachedCriteria query = DetachedCriteria.forClass(Webshell.class);
		query.add(Restrictions.eq("url", url));
		List<Webshell> list = webshellManager.getListByDetachedCriteria(query);
		webshell = list.get(0);
		
		return SUCCESS;
	}
	
	public String saveWebshell(){
		String url = this.getRequest().getParameter("url");
		String nameC = this.getRequest().getParameter("nameC");
		String corpSubName = this.getRequest().getParameter("subCorpList");
		
		DetachedCriteria query = DetachedCriteria.forClass(Webshell.class);
		query.add(Restrictions.eq("url", url));
		Webshell ws = webshellManager.getListByDetachedCriteria(query).get(0);
		 
		if(nameC != null && !"".equals(nameC)){
			ws.setNameC(nameC);
		}
		
		if(corpSubName != null && !"".equals(corpSubName)){
			ws.setCorpSubName(corpSubName);
		}
		webshellManager.save(ws);
		
		return SUCCESS;
	}
	
	public String editWebshellHandle(){
		String id = this.getRequest().getParameter("id");
		
		wshId = Long.parseLong(id);
		DetachedCriteria wshQuery = DetachedCriteria.forClass(WebshellHandle.class);
		wshQuery.addOrder(Order.desc("handleDate"));
		wshQuery.add(Restrictions.eq("webshellId", wshId));
		wshList = webshellHandleManager.getListByDetachedCriteria(wshQuery);

		this.getRequest().setAttribute("wshListSize", wshList.size());
		
		return SUCCESS;
	}
	
	public String saveWSH() throws ParseException{
		String handleDate = getRequest().getParameter("handleDate");
		String handleStatus = getRequest().getParameter("handleStatus");
		String memo = getRequest().getParameter("memo");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		DetachedCriteria query = DetachedCriteria.forClass(WebshellHandle.class);
		query.add(Restrictions.eq("handleDate", sdf.parse(handleDate)));
		query.add(Restrictions.eq("webshellId", wshId));
		List<WebshellHandle> list = webshellHandleManager.getListByDetachedCriteria(query);
		if(list.size()>0){
			WebshellHandle wsh = list.get(0);
			wsh.setHandleDate(sdf.parse(handleDate));
			wsh.setStatus(handleStatus);
			wsh.setAddopr((String)getSession().getAttribute("contact"));
			if(wsh.getMemo() == null || "".equals(wsh.getMemo())){
				wsh.setMemo(memo);
			}else{
				if(memo != null && !"".equals(memo)){
					wsh.setMemo(memo + ";" + wsh.getMemo());
				}
				
			}
			
			wsh.setCreateTime(new Date());
			webshellHandleManager.save(wsh);
		}else{
			WebshellHandle tempWSH = new WebshellHandle();
			tempWSH.setAddopr((String)getSession().getAttribute("contact"));
			tempWSH.setHandleDate(sdf.parse(handleDate));
			tempWSH.setStatus(handleStatus);
			tempWSH.setMemo(memo);
			tempWSH.setWebshellId(wshId);
			tempWSH.setCreateTime(new Date());
			webshellHandleManager.save(tempWSH);
		}
		actionLoger.log((UserInfo)this.getSession().getAttribute("currentUser"), "saveWSH", "保存webleak_handle信息", getRequest());
		return SUCCESS;
	}
	
	

	public List<WebshellCorpView> getWebshellList() {
		return webshellList;
	}

	public void setWebshellList(List<WebshellCorpView> webshellList) {
		this.webshellList = webshellList;
	}

	public void setWebshellCorpViewManager(
			WebshellCorpViewManager webshellCorpViewManager) {
		this.webshellCorpViewManager = webshellCorpViewManager;
	}

	public void setWebshellNodeViewManager(
			WebshellNodeViewManager webshellNodeViewManager) {
		this.webshellNodeViewManager = webshellNodeViewManager;
	}

	public void setWebshellViewManager(WebshellViewManager webshellViewManager) {
		this.webshellViewManager = webshellViewManager;
	}

	public Webshell getWebshell() {
		return webshell;
	}

	public void setWebshell(Webshell webshell) {
		this.webshell = webshell;
	}

	public void setWebshellManager(WebshellManager webshellManager) {
		this.webshellManager = webshellManager;
	}
	
	public void setDataDictManager(DataDictManager dataDictManager) {
		this.dataDictManager = dataDictManager;
	}

	public List getSubCorpList() {
		subCorpList = dataDictManager.getDataDictByParams("W3SP_WEBLEAK", "CORP_SUB_NAME");
		return subCorpList;
	}
	
	public List<WebshellHandle> getWshList() {
		return wshList;
	}

	public void setWshList(List<WebshellHandle> wshList) {
		this.wshList = wshList;
	}

	public void setWebshellHandleManager(WebshellHandleManager webshellHandleManager) {
		this.webshellHandleManager = webshellHandleManager;
	}

	public Long getWshId() {
		return wshId;
	}

	public void setWshId(Long wshId) {
		this.wshId = wshId;
	}
	
	public List getHandleStatusList() {
		handleStatusList = dataDictManager.getDataDictByParams("W3SP_WEBLEAK_HANDLE", "STATUS");
		return handleStatusList;
	}

	public void setHandleStatusList(List handleStatusList) {
		this.handleStatusList = handleStatusList;
	}
}
