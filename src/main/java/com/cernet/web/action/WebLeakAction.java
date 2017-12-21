package com.cernet.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.cernet.cwui.pagination.impl.PaginatedListHibernateImpl;
import com.cernet.model.LeakCmList;
import com.cernet.model.LeakColMonitor;
import com.cernet.model.LeakInfoList;
import com.cernet.model.LeakPmList;
import com.cernet.model.LeakPushMonitor;
import com.cernet.model.UserInfo;
import com.cernet.model.WebLeak;
import com.cernet.model.WebLeakCView;
import com.cernet.model.WebLeakHandle;
import com.cernet.model.WebLeakInfo;
import com.cernet.model.WebLeakModel;
import com.cernet.model.WebLeakNView;
import com.cernet.model.WebLeakRealTimeView;
import com.cernet.service.DataDictManager;
import com.cernet.service.WebLeakCViewManager;
import com.cernet.service.WebLeakHandleManager;
import com.cernet.service.WebLeakInfoManager;
import com.cernet.service.WebLeakManager;
import com.cernet.service.WebLeakNViewManager;
import com.cernet.service.WebLeakRealTimeViewManager;
import com.cernet.util.DateUtil;
import com.cernet.util.JsonBinder;
import com.google.gson.Gson;
import com.mysql.jdbc.StringUtils;

public class WebLeakAction extends BaseAction {
	
	private WebLeak webLeak;
	private List<WebLeak> webLeakList;
	private List<WebLeakModel> corpSubNameList;
	private List<WebLeak> nameCList;
//	private List<WebLeakModel> modelList;
	private List ldList ;
	private List subCorpList;
	private List dealStatusList;
	private List handleStatusList;
	private DataDictManager dataDictManager;
	private List<WebLeakHandle> wlhList;
	private WebLeakHandleManager webLeakHandleManager;
	private WebLeakCViewManager webLeakCViewManager;
	private List<WebLeakCView> webLeakCVList;
	private WebLeakNViewManager webLeakNViewManager;
	private List<WebLeakNView> webLeakNViewList; 
	private WebLeakRealTimeViewManager webLeakRealTimeViewManager;
	private List<WebLeakRealTimeView> webLeakRTList;
	private WebLeakRealTimeView webLeakRealTimeView;
	private WebLeakInfoManager webLeakInfoManager;
	private Long wlhId;
	
	public WebLeakRealTimeView getWebLeakRealTimeView() {
		return webLeakRealTimeView;
	}

	public void setWebLeakRealTimeView(WebLeakRealTimeView webLeakRealTimeView) {
		this.webLeakRealTimeView = webLeakRealTimeView;
	}

	public List<WebLeakRealTimeView> getWebLeakRTList() {
		return webLeakRTList;
	}

	public void setWebLeakRTList(List<WebLeakRealTimeView> webLeakRTList) {
		this.webLeakRTList = webLeakRTList;
	}

	public void setWebLeakRealTimeViewManager(
			WebLeakRealTimeViewManager webLeakRealTimeViewManager) {
		this.webLeakRealTimeViewManager = webLeakRealTimeViewManager;
	}

	public List<WebLeakNView> getWebLeakNViewList() {
		return webLeakNViewList;
	}

	public void setWebLeakNViewList(List<WebLeakNView> webLeakNViewList) {
		this.webLeakNViewList = webLeakNViewList;
	}

	public void setWebLeakNViewManager(WebLeakNViewManager webLeakNViewManager) {
		this.webLeakNViewManager = webLeakNViewManager;
	}

	public Long getWlhId() {
		return wlhId;
	}

	public void setWlhId(Long wlhId) {
		this.wlhId = wlhId;
	}

	public List<WebLeakHandle> getWlhList() {
		return wlhList;
	}

	public void setWlhList(List<WebLeakHandle> wlhList) {
		this.wlhList = wlhList;
	}

	public void setWebLeakHandleManager(WebLeakHandleManager webLeakHandleManager) {
		this.webLeakHandleManager = webLeakHandleManager;
	}
	
	public List getHandleStatusList() {
		handleStatusList = dataDictManager.getDataDictByParams("W3SP_WEBLEAK_HANDLE", "STATUS");
		return handleStatusList;
	}

	public void setHandleStatusList(List handleStatusList) {
		this.handleStatusList = handleStatusList;
	}

	public List getLdList() {
		ldList = dataDictManager.getDataDictByParams("W3SP_WEBLEAK", "LEAK_TYPE");
		return ldList;
	}

	public void setLdList(List ldList) {
		this.ldList = ldList;
	}

	public List getSubCorpList() {
		subCorpList = dataDictManager.getDataDictByParams("W3SP_WEBLEAK", "CORP_SUB_NAME");
		return subCorpList;
	}

	public void setSubCorpList(List subCorpList) {
		this.subCorpList = subCorpList;
	}

	public List getDealStatusList() {
		dealStatusList = dataDictManager.getDataDictByParams("W3SP_WEBLEAK", "STATUS");
		return dealStatusList;
	}

	public void setDealStatusList(List dealStatusList) {
		this.dealStatusList = dealStatusList;
	}

	public void setDataDictManager(DataDictManager dataDictManager) {
		this.dataDictManager = dataDictManager;
	}

//	public List<WebLeakModel> getModelList() {
//		return modelList;
//	}
//
//	public void setModelList(List<WebLeakModel> modelList) {
//		this.modelList = modelList;
//	}

	

	public List<WebLeakModel> getCorpSubNameList() {
		return corpSubNameList;
	}

	public void setCorpSubNameList(List<WebLeakModel> corpSubNameList) {
		this.corpSubNameList = corpSubNameList;
	}

	public List<WebLeak> getNameCList() {
		return nameCList;
	}

	public void setNameCList(List<WebLeak> nameCList) {
		this.nameCList = nameCList;
	}


	private WebLeakManager webLeakManager;

	public WebLeak getWebLeak() {
		return webLeak;
	}

	public void setWebLeak(WebLeak webLeak) {
		this.webLeak = webLeak;
	}

	public List<WebLeak> getWebLeakList() {
		return webLeakList;
	}

	public void setWebLeakList(List<WebLeak> webLeakList) {
		this.webLeakList = webLeakList;
	}

	public void setWebLeakManager(WebLeakManager webLeakManager) {
		this.webLeakManager = webLeakManager;
	}
	
	public String list(){
		UserInfo user = (UserInfo)this.getSession().getAttribute("currentUser");
//		Map<String, Object> paraMap = new HashMap<String, Object>();
//		paraMap.put("user", user);
//		modelList = webLeakManager.getWebLeakModelList(paraMap);

		DetachedCriteria query = DetachedCriteria.forClass(WebLeakCView.class);
		if(!"技术开发部".equals(user.getDepartment())){
			query.add(Restrictions.eq("corpSubName", user.getDepartment()));
		}
		query.addOrder(Order.desc("statusERR"));
		webLeakCVList = webLeakCViewManager.getListByDetachedCriteria(query);
		return SUCCESS;
	}
	
	/**
	 * TODO:new code
	 * @author zhaohn
	 * @return
	 */
	//struts2漏洞处置监控
	public String showLeakStruts2() {
		DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
		DetachedCriteria dc = DetachedCriteria.forClass(WebLeakInfo.class);
		dc.setProjection(Projections.distinct(Projections.property("leakType")));
		int fullSize = 0;
		List webLeakInfoList = webLeakInfoManager.getListByDetachedCriteria(dc);
		if (webLeakInfoList != null) {
			/*fullSize = webLeakInfoList.size();
			System.out.println("---------------fullSize:"+fullSize);
		*/
			fullSize=4;
		}
		d.add(Restrictions.eq("leakType", "STRUTS2漏洞"));
		//.add(Restrictions.ne("status", "1"))
		d.setProjection(Projections.projectionList()
				.add(Projections.count("leakType"), "totalNum")
				.add(Projections.max("modTime"), "lastTime")//最近更新时间
				.add(Projections.groupProperty("status"),"status")
				);
		d.setResultTransformer(Transformers.aliasToBean(LeakInfoList.class));
		plist = new PaginatedListHibernateImpl(page, pagesize, fullSize, sort,
				dir);
		plist.setFullListSize(fullSize);
		try {
			plist.setDetachedCriteria(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List infoList = webLeakInfoManager.getListByDetachedCriteria(d);
		List<LeakInfoList> leakInfoList = new ArrayList<LeakInfoList>();
		for (int i = 0; i < infoList.size(); i++) {// 循环遍历proName查出LastTotalCount
			LeakInfoList leakList = (LeakInfoList) infoList.get(i);
			String status=leakList.getStatus();
			//0:已修复；1:未处理;2： 链接打不开;3:网络不通
			if(status.equals("1")){
				leakList.setStatus("未处理");
			}else if(status.equals("0")){
				leakList.setStatus("已修复");
			}else if (status.equals("2")) {
				leakList.setStatus("链接打不开");
			}else if (status.equals("3")) {
				leakList.setStatus("网络不通");
			}
			Date lasttime = leakList.getLastTime();
			
			long hours = getDayHours(new Date(), lasttime);
			
			System.out.println("运行时间："+hours);
			if(hours>24l*7){
				leakList.setRunStatus("异常");
			}else {
				leakList.setRunStatus("正常");
				leakList.setRunHours(hours);
			}
			leakInfoList.add(leakList);
		}
		plist.setList(leakInfoList);
		return SUCCESS;
	}
	public String ajaxGetStruts2Info1() throws IOException {
		DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
		d.setProjection(Projections.projectionList()
				.add(Projections.count("leakType"), "totalNum"));
		d.add(Restrictions.eq("status", "1"))
		.add(Restrictions.eq("leakType", "STRUTS2漏洞"));
		d.setResultTransformer(Transformers.aliasToBean(LeakInfoList.class));
		List infoList = webLeakInfoManager.getListByDetachedCriteria(d);
		/*int s = infoList.size();
		System.out.println(s);*/
		Gson gson = new Gson();
		String json="";
		for (Object object : infoList) {
			json += gson.toJson(object);
		}
		ServletActionContext.getResponse().getWriter().print(json);
		
		return null;
	}
	public String ajaxGetStruts2Info0() throws IOException {
		DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
		d.setProjection(Projections.projectionList()
				.add(Projections.count("leakType"), "totalNum"));
		d.add(Restrictions.eq("status", "0"))
		.add(Restrictions.eq("leakType", "STRUTS2漏洞"));

		d.setResultTransformer(Transformers.aliasToBean(LeakInfoList.class));
		List infoList = webLeakInfoManager.getListByDetachedCriteria(d);
		Gson gson = new Gson();
		String json="";
		for (Object object : infoList) {
			json += gson.toJson(object);
		}
		//System.out.println("json:" + json);
		ServletActionContext.getResponse().getWriter().print(json);
		
		return null;
	}
	public String ajaxGetStruts2Info3() throws IOException {
		DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
		d.setProjection(Projections.projectionList()
				.add(Projections.count("leakType"), "totalNum"));
		d.add(Restrictions.eq("status", "3"))
		.add(Restrictions.eq("leakType", "STRUTS2漏洞"));

		d.setResultTransformer(Transformers.aliasToBean(LeakInfoList.class));
		List infoList = webLeakInfoManager.getListByDetachedCriteria(d);
		
		Gson gson = new Gson();
		String json="";
		for (Object object : infoList) {
			json += gson.toJson(object);
		}
		ServletActionContext.getResponse().getWriter().print(json);
		return null;
	}
	public String ajaxGetStruts2Info2() throws IOException {
		DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
		d.setProjection(Projections.projectionList()
				.add(Projections.count("leakType"), "totalNum"));
		d.add(Restrictions.eq("status", "2"))
		.add(Restrictions.eq("leakType", "STRUTS2漏洞"));

		d.setResultTransformer(Transformers.aliasToBean(LeakInfoList.class));
		List infoList = webLeakInfoManager.getListByDetachedCriteria(d);
		/*int s = infoList.size();
		System.out.println(s);*/
		Gson gson = new Gson();
		String json="";
		for (Object object : infoList) {
			json += gson.toJson(object);
		}
		//System.out.println("json:" + json);
		ServletActionContext.getResponse().getWriter().print(json);
		
		return null;
	}
	public String monthReport() {
		//获取时间
		Calendar begin=Calendar.getInstance();
		Calendar end=Calendar.getInstance();
		begin.set(2016, 7, 1);
		end.set(2016,8,30);
		
//		DetachedCriteria d1=DetachedCriteria.forClass(WebLeakInfo.class);
		
		DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
		DetachedCriteria dc = DetachedCriteria.forClass(WebLeakInfo.class);
		dc.add(Restrictions.between("modTime", begin.getTime(),end.getTime()));
		dc.setProjection(Projections.distinct(Projections.property("leakType")));
		
		/*d1.add(Restrictions.ne("status", "0"));
		d1.add(Restrictions.between("modTime", begin.getTime(),end.getTime()));
		d1.setProjection(Projections.projectionList()
				.add(Projections.count("leakType"),"notFinished")
				.add(Projections.groupProperty("leakType"),"leakType"));
		d1.setResultTransformer(Transformers.aliasToBean(LeakInfoList.class));
		List nfList=webLeakInfoManager.getListByDetachedCriteria(d1);
		List<LeakInfoList> leakInfoLists = new ArrayList<LeakInfoList>();
		List<Integer> nfl=new ArrayList<Integer>();
		for (int i = 0; i < nfList.size(); i++) {
			LeakInfoList leakInfoList=(LeakInfoList) nfList.get(i);
			nfl.add(leakInfoList.getNotFinished());
		}*/
		
		int fullSize = 0;
		List webLeakInfoList = webLeakInfoManager.getListByDetachedCriteria(dc);
		if (webLeakInfoList != null) {
			fullSize = webLeakInfoList.size();
			System.out.println("---------------fullSize:"+fullSize);
		}
		d.add(Restrictions.between("modTime", begin.getTime(),end.getTime()));
		d.setProjection(Projections.projectionList()
				.add(Projections.count("leakType"), "totalNum")
				
				.add(Projections.max("modTime"), "lastTime")
				.add(Projections.groupProperty("leakType"),"leakType")
				);
		
		d.setResultTransformer(Transformers.aliasToBean(LeakInfoList.class));
		plist = new PaginatedListHibernateImpl(page, fullSize, fullSize, sort,dir);
		plist.setFullListSize(fullSize);
		try {
			plist.setDetachedCriteria(d);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List infoList = webLeakInfoManager.getListByDetachedCriteria(d);
		List<LeakInfoList> leakInfoList = new ArrayList<LeakInfoList>();
		
//		System.out.println(nfList.size()+"nf<<<<\t>>>>total"+infoList.size());
		// 循环遍历proName查出LastTotalCount
		for (int i = 0; i < infoList.size(); i++) {
			LeakInfoList leakList = (LeakInfoList) infoList.get(i);
			//int totalNum=leakList.getTotalNum();
			if(leakList.getLeakType()==null||leakList.getLeakType().equals("")){
				leakList.setLeakType("未知");
			}
			
			int nf=leakList.getNotFinished();
			int tn=leakList.getTotalNum();
			if(tn!=0){
				leakList.setPercent(nf/tn);
			}
			leakInfoList.add(leakList);
		}
		
		plist.setList(leakInfoList);
		return SUCCESS;
	}
	/**
	 * end
	 */
	public static long getDayHours(Date beginDate, Date endDate) {
		long hours = 0;
		hours = (beginDate.getTime() - endDate.getTime()) / (60 * 60 * 1000);
		return hours;
	}
	public List<WebLeakCView> getWebLeakCVList() {
		return webLeakCVList;
	}

	public void setWebLeakCVList(List<WebLeakCView> webLeakCVList) {
		this.webLeakCVList = webLeakCVList;
	}

	public void setWebLeakCViewManager(WebLeakCViewManager webLeakCViewManager) {
		this.webLeakCViewManager = webLeakCViewManager;
	}
	public void subList(){
		DetachedCriteria query = DetachedCriteria.forClass(WebLeakNView.class);
		
		String corpSubName = "";
		if(getRequest().getParameter("corpSubName") != null && !"".equals(getRequest().getParameter("corpSubName"))){
			corpSubName = getRequest().getParameter("corpSubName");
			query.add(Restrictions.eq("corpSubName", corpSubName));
			query.add(Restrictions.ne("statusERR", 0L));
			query.addOrder(Order.desc("statusERR"));
		}
		webLeakNViewList = webLeakNViewManager.getListByDetachedCriteria(query);
		
		Long sumHost = 0L;
		Long sumUrl = 0L;
		Long sumErr = 0L;
		for(int i=0;i<webLeakNViewList.size();i++){
			sumErr += webLeakNViewList.get(i).getStatusERR();
			sumUrl  += webLeakNViewList.get(i).getCountURL();
			sumHost += webLeakNViewList.get(i).getCountHost();
		}
		this.getSession().setAttribute("sumErrWL", sumErr);
		this.getSession().setAttribute("sumUrl", sumUrl);
		this.getSession().setAttribute("sumHost", sumHost);
		try {
			JsonBinder jb = JsonBinder.buildNonNullBinder();
//			jb.setDateFormat("yyyy-MM-dd");
			String pageStr = jb.toJson(webLeakNViewList);
			getResponse().getWriter().write(pageStr);
			getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
@SuppressWarnings("unchecked")
public void subList2(){
	
		try {
			String nameC = "";
			
			String corpSubName = "";

			if(getRequest().getParameter("nameC") != null && !"".equals(getRequest().getParameter("nameC"))){
				nameC = getRequest().getParameter("nameC");
			}
			
			if(getRequest().getParameter("corpSubName") != null && !"".equals(getRequest().getParameter("corpSubName"))){
				corpSubName = getRequest().getParameter("corpSubName");
			}

			if(!"".equals(nameC) && !"".equals(corpSubName)){
				DetachedCriteria queryCriteria = DetachedCriteria
						.forClass(WebLeakRealTimeView.class);
				queryCriteria.add(Restrictions.eq("nameC", nameC));
				queryCriteria.add(Restrictions.eq("corpSubName", corpSubName));
				
//				UserInfo user = (UserInfo)this.getSession().getAttribute("currentUser");
//				if(!"技术开发部".equals(user.getDepartment())){
//					queryCriteria.add(Restrictions.ne("status", "已修复"));
//				}
				queryCriteria.addOrder(Order.desc("status"));
				webLeakRTList = webLeakRealTimeViewManager.getListByDetachedCriteria(queryCriteria);
			}
			JsonBinder jb = JsonBinder.buildNonNullBinder();
			jb.setDateFormat("yyyy-MM-dd");
			String pageStr2 = jb.toJson(webLeakRTList);
			getResponse().getWriter().write(pageStr2);
			getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String searchList(){
		Map<String, String> fieldsMap = new HashMap<String, String>();
		DetachedCriteria queryCriteria = DetachedCriteria
				.forClass(WebLeakRealTimeView.class);
		
		String url = webLeakRealTimeView.getUrl();
		String nameC = webLeakRealTimeView.getNameC();
		String corpSubName = webLeakRealTimeView.getCorpSubName();
		String leakType = webLeakRealTimeView.getLeakType();
		String status = webLeakRealTimeView.getStatus();
		String prodName = webLeakRealTimeView.getProdName();
		String softName = webLeakRealTimeView.getSoftName();
		String score = webLeakRealTimeView.getScore();
		String eventDate="";

		if(webLeakRealTimeView.getEventDate()!=null){
			eventDate = new SimpleDateFormat("yyyy-MM-dd").format(webLeakRealTimeView.getEventDate());
			getRequest().getSession().setAttribute("eventDate", eventDate);
		}
		//Date date = DateUtil.convertStringToDate("yyyy-MM-dd", eventDate);
				
			if(url!=null&&!"".equals(url.trim())){
				fieldsMap.put("url", url.trim());
			}
			if(nameC!=null&&!"".equals(nameC.trim())){
				fieldsMap.put("nameC", nameC.trim());
			}
			if(corpSubName!=null&&!"".equals(corpSubName.trim())){
				fieldsMap.put("corpSubName", corpSubName.trim());
			}
			if(leakType!=null&&!"".equals(leakType.trim())){
				fieldsMap.put("leakType", leakType.trim());
			}
			if(status!=null&&!"".equals(status.trim())){
				fieldsMap.put("status", status.trim());
			}else{
				webLeakRealTimeView.setStatus("");
			}
			if(eventDate!=null&&!"".equals(eventDate.trim())){
				fieldsMap.put("eventDate", eventDate.trim());
			}
			if(prodName!=null&&!"".equals(prodName.trim())){
				fieldsMap.put("prodName", prodName.trim());
			}
			if(softName!=null&&!"".equals(softName.trim())){
				fieldsMap.put("softName", softName.trim());
			}
			if(score!=null&&!"".equals(score.trim())){
				fieldsMap.put("score", score.trim());
			}
		
		plist = webLeakRealTimeViewManager.getListByDetachedCriteriaPage(fieldsMap,page, pagesize, sort,
				dir);

		
		return SUCCESS;
		
	}
	public String edit(){
       String url = "";

		if(getRequest().getParameter("url") != null && ! "".equals(getRequest().getParameter("url"))){
			url = getRequest().getParameter("url");
		}
		DetachedCriteria queryCriteria = DetachedCriteria
				.forClass(WebLeak.class);
		
		queryCriteria.add(Restrictions.like("url", "%"+url.trim()+"%"));
		
		List<WebLeak> demoList = webLeakManager.getListByDetachedCriteria(queryCriteria);
		
		try {
			if(demoList != null && demoList.size()>0){
				webLeak = demoList.get(0);
				String sucMsg=URLDecoder.decode(new String("成功查找到信息！"), "utf-8"); 
				getRequest().setAttribute("searchMsg", "SUCEESS");
				System.out.println(sucMsg);
			}
			else{
				String errMsg=URLDecoder.decode(new String("can not find the item,you can ctreate"), "utf-8");
				getRequest().setAttribute("searchFlag", "1");
				getRequest().setAttribute("searchUrl", url);
				getRequest().setAttribute("searchMsg", "No Resault");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return SUCCESS;
	}
	
	
	public String save(){
		String nameC2="";
		String nameC= "";
		String url = "";
		String path="";
		String corpSubName = "";
		String ctrledTag = "";
		String leakType = "";
		String eventDate = "";
		UserInfo user = (UserInfo)this.getSession().getAttribute("currentUser");
		if(!"技术开发部".equals(user.getDepartment())){
			return "noRight";
		}

		if(getRequest().getParameter("url") != null && !"".equals(getRequest().getParameter("url"))){
			url = getRequest().getParameter("url");
		}

		DetachedCriteria queryCriteria = DetachedCriteria.forClass(WebLeak.class);
		
		queryCriteria.add(Restrictions.like("url", "%"+url+"%"));
		
		List weblList = webLeakManager.getListByDetachedCriteria(queryCriteria);
		
		nameC2 = getRequest().getParameter("nameC2");
		if(weblList != null && weblList.size() > 0){
			webLeak = (WebLeak) weblList.get(0);
			if(getRequest().getParameter("nameC") != null && !"".equals(getRequest().getParameter("nameC"))){
				nameC = getRequest().getParameter("nameC");
				webLeak.setNameC(nameC);
			}
			if(getRequest().getParameter("path") != null && !"".equals(getRequest().getParameter("path"))){
				path = getRequest().getParameter("path");
				webLeak.setPath(path);
			}
			if(getRequest().getParameter("subCorpList") != null && !"".equals(getRequest().getParameter("subCorpList"))){
				corpSubName = getRequest().getParameter("subCorpList");
				
			}
			webLeak.setCorpSubName(corpSubName);
			if(getRequest().getParameter("ldList") != null && !"".equals(getRequest().getParameter("ldList"))){
				leakType = getRequest().getParameter("ldList");
				webLeak.setLeakType(leakType);
			}else{
				webLeak.setLeakType("");
			}
			
			
			try {
				if(getRequest().getParameter("eventDate") != null && !"".equals(getRequest().getParameter("eventDate"))){
					eventDate = getRequest().getParameter("eventDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    webLeak.setEventDate(sdf.parse(eventDate));
					
				}else{
					
					webLeak.setEventDate(new Date());
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			webLeak.setNameC2(nameC2);//二级单位名称 2013-10-10日新增
			webLeakManager.save(webLeak);
		}
		else{
			
			webLeak = new WebLeak();
			
			webLeak.setUrl(url);
			
			if(getRequest().getParameter("nameC") != null && !"".equals(getRequest().getParameter("nameC"))){
				nameC = getRequest().getParameter("nameC");
				webLeak.setNameC(nameC);
			}
			if(getRequest().getParameter("path") != null && !"".equals(getRequest().getParameter("path"))){
				path = getRequest().getParameter("path");
				webLeak.setPath(path);
			}
			if(getRequest().getParameter("subCorpList") != null && !"".equals(getRequest().getParameter("subCorpList"))){
				corpSubName = getRequest().getParameter("subCorpList");
				
			}
			webLeak.setCorpSubName(corpSubName);
			if(getRequest().getParameter("ldList") != null && !"".equals(getRequest().getParameter("ldList"))){
				leakType = getRequest().getParameter("ldList");
				webLeak.setLeakType(leakType);
			}else{
				webLeak.setLeakType("");
			}
			
			try {
				if(getRequest().getParameter("eventDate") != null && !"".equals(getRequest().getParameter("eventDate"))){
					eventDate = getRequest().getParameter("eventDate");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    webLeak.setEventDate(sdf.parse(eventDate));
					
				}else{
					
					webLeak.setEventDate(new Date());
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ctrledTag = url.substring(6);
			ctrledTag = ctrledTag.split("/")[1];
			webLeak.setCtrledTag(ctrledTag);
			webLeak.setCreateTime(new Date());
			webLeak.setNameC2(nameC2);//二级单位名称 2013-10-10日新增
			webLeakManager.save(webLeak);
			
		}
		
		try {
			String saveMsg=URLDecoder.decode(new String("数据保存成功！"), "utf-8"); 
			getRequest().getSession().setAttribute("msg", "SUCCESS");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		actionLoger.log((UserInfo)this.getSession().getAttribute("currentUser"), "save", "保存webleak信息", getRequest());
		return SUCCESS;
	}
	
	
	public String editWebLeakHandle(){
		String id = getRequest().getParameter("id");
		wlhId = Long.parseLong(id);
		DetachedCriteria wlhQuery = DetachedCriteria.forClass(WebLeakHandle.class);
		wlhQuery.addOrder(Order.desc("handleDate"));
		wlhQuery.add(Restrictions.eq("webLeakId", wlhId));
		wlhList = webLeakHandleManager.getListByDetachedCriteria(wlhQuery);

		this.getRequest().setAttribute("wlhListSize", wlhList.size());
		return SUCCESS;
	}
	
	public String saveWLH() throws ParseException{
		String handleDate = getRequest().getParameter("handleDate");
		String handleStatus = getRequest().getParameter("handleStatus");
		String memo = getRequest().getParameter("memo");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DetachedCriteria query = DetachedCriteria.forClass(WebLeakHandle.class);
		query.add(Restrictions.eq("handleDate", sdf.parse(handleDate)));
		query.add(Restrictions.eq("webLeakId", wlhId));
		List<WebLeakHandle> list = webLeakHandleManager.getListByDetachedCriteria(query);
		if(list.size()>0){
			WebLeakHandle wlh = list.get(0);
			wlh.setHandleDate(sdf.parse(handleDate));
			wlh.setStatus(handleStatus);
			wlh.setAddopr((String)getSession().getAttribute("contact"));
			if(wlh.getMemo() == null || "".equals(wlh.getMemo())){
				wlh.setMemo(memo);
			}else{
				if(memo != null && !"".equals(memo)){
					wlh.setMemo(memo + ";" + wlh.getMemo());
				}
			}
			wlh.setCreateTime(new Date());
			webLeakHandleManager.save(wlh);
		}else{
			WebLeakHandle tempWLH = new WebLeakHandle();
			tempWLH.setAddopr((String)getSession().getAttribute("contact"));
			tempWLH.setHandleDate(sdf.parse(handleDate));
			tempWLH.setStatus(handleStatus);
			tempWLH.setMemo(memo);
			tempWLH.setWebLeakId(wlhId);
			tempWLH.setCreateTime(new Date());
			webLeakHandleManager.save(tempWLH);
		}
		actionLoger.log((UserInfo)this.getSession().getAttribute("currentUser"), "saveWLH", "保存webleak_handle信息", getRequest());
		//主表w3sp_webleak里的漏洞状态STATUS字段也要和w3sp_webleak_handle表STATUS保持一致  修改日期：2016-07-11
		DetachedCriteria wldc = DetachedCriteria.forClass(WebLeakInfo.class);
		wldc.add(Restrictions.eq("id", wlhId));
		List<WebLeakInfo> webLeakList = webLeakInfoManager.getListByDetachedCriteria(wldc);
		if (webLeakList.size()>0) {
			WebLeakInfo webLeakInfo=webLeakList.get(0);
			String[] strStatus={"0","1","2","3"};//0:已修复；1:未处理;2： 链接打不开;3:网络不通
			for (int i = 0; i < strStatus.length; i++) {
				if ("已修复".equals(handleStatus)) {
					webLeakInfo.setStatus(strStatus[0]);
				}
				else if ("未处理".equals(handleStatus)) {
					webLeakInfo.setStatus(strStatus[1]);
				}
				else if ("链接打不开".equals(handleStatus)) {
					webLeakInfo.setStatus(strStatus[2]);
				}
				else if ("网络不通".equals(handleStatus)) {
					webLeakInfo.setStatus(strStatus[3]);
				}
			}
			webLeakInfoManager.save(webLeakInfo);
		}
		actionLoger.log((UserInfo)this.getSession().getAttribute("currentUser"), "saveWLH", "保存webleak的status信息", getRequest());
		return SUCCESS;
	}

	public WebLeakInfoManager getWebLeakInfoManager() {
		return webLeakInfoManager;
	}

	public void setWebLeakInfoManager(WebLeakInfoManager webLeakInfoManager) {
		this.webLeakInfoManager = webLeakInfoManager;
	}
	
}
