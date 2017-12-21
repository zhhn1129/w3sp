package com.cernet.web.action;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.bcel.generic.NEW;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.expression.ParseException;

import com.cernet.dao.ArticleDao;
import com.cernet.dao.WebLeakInfoDao;
import com.cernet.model.Article;
import com.cernet.model.HighPush;
import com.cernet.model.LeakPushMonitor;
import com.cernet.model.UserInfo;
import com.cernet.model.WebLeakInfo;
import com.cernet.service.ArticleManager;
import com.cernet.service.HighPushManager;
import com.cernet.service.LeakPushMonitorManager;
import com.cernet.service.WebLeakInfoManager;
import com.cernet.util.Jacksons;
import com.cernet.util.Md5;
import com.cernet.utils.log.Actionlog;
import com.cernet.utils.log.ActionlogManager;
import com.cernet.wechat.WechatHelper;

public class ArticleAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArticleManager articleManager; 
	private WebLeakInfoManager webLeakInfoManager;
	
	private ArticleDao articleDao;
	private WebLeakInfoDao webLeakInfoDao;
	
	private Article article;
	private WebLeakInfo webLeakInfo;
	
	private ActionlogManager actionlogManager;
	
	private List artls;
	
	private LeakPushMonitorManager leakPushMonitorManager;

	public ActionlogManager getActionlogManager() {
		return actionlogManager;
	}

	public void setActionlogManager(ActionlogManager actionlogManager) {
		this.actionlogManager = actionlogManager;
	}

	public ArticleManager getArticleManager() {
		return articleManager;
	}

	public void setArticleManager(ArticleManager articleManager) {
		this.articleManager = articleManager;
	}
	public List getArtls() {
		return artls;
	}

	public void setArtls(List artls) {
		this.artls = artls;
	}

	public ArticleDao getArticleDao() {
		return articleDao;
	}

	public void setArticleDao(ArticleDao articleDao) {
		this.articleDao = articleDao;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}
	public LeakPushMonitorManager getLeakPushMonitorManager() {
		return leakPushMonitorManager;
	}

	public void setLeakPushMonitorManager(
			LeakPushMonitorManager leakPushMonitorManager) {
		this.leakPushMonitorManager = leakPushMonitorManager;
	}

	public WebLeakInfoManager getWebLeakInfoManager() {
		return webLeakInfoManager;
	}

	public void setWebLeakInfoManager(WebLeakInfoManager webLeakInfoManager) {
		this.webLeakInfoManager = webLeakInfoManager;
	}

	public WebLeakInfoDao getWebLeakInfoDao() {
		return webLeakInfoDao;
	}

	public void setWebLeakInfoDao(WebLeakInfoDao webLeakInfoDao) {
		this.webLeakInfoDao = webLeakInfoDao;
	}

	public WebLeakInfo getWebLeakInfo() {
		return webLeakInfo;
	}

	public void setWebLeakInfo(WebLeakInfo webLeakInfo) {
		this.webLeakInfo = webLeakInfo;
	}

/*
 * 公众号-业界动态-四个栏目
 * 
*/
	public String getArticleList(){
		
		String colId = getRequest().getParameter("colId");
		getRequest().setAttribute("colId", colId);
		String alias = "ARTICLE"; //查詢時的table名
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Article.class,alias);
		
		ProjectionList pList = Projections.projectionList();  
		pList.add(Projections.property(alias + "." + "articleId").as("articleId"));  
		pList.add(Projections.property(alias + "." + "articleTitle").as("articleTitle"));
		pList.add(Projections.property(alias + "." + "articlePubTime").as("articlePubTime"));
		
		dCriteria.setProjection(pList);  
		dCriteria.setResultTransformer(Transformers.aliasToBean(Article.class));
		dCriteria.addOrder(Order.desc("articleId"));

		if(!"".equals(colId)&&colId!=null){
			dCriteria.add(Restrictions.eq("colId", colId));
		}
		artls = articleDao.getListByDetachedCriteriaPage(dCriteria, page, pagesize);
		DetachedCriteria dc = DetachedCriteria.forClass(Article.class);
		if(!"".equals(colId)&&colId!=null){
			dc.add(Restrictions.eq("colId", colId));
		}
		int totalCounts = articleDao.getCountByDetachedCriteria(dc);
		int totalPage = (totalCounts+pagesize-1)/pagesize;
		getRequest().setAttribute("totalPage", totalPage);
		getRequest().setAttribute("currentpage", page);
		return SUCCESS;
	}
	/*
	 * 公众号-业界动态-四个栏目
	 * 
	*/	
	public String getArticleById(){
		
		String articleId = getRequest().getParameter("articleId");
		
		article = articleDao.get(articleId);
		
		return SUCCESS;
	}
	/*
	 * 点击加载更多
	 * 
	*/
	public void getMore(){

		String colId = getRequest().getParameter("colId");
		String currentpage = getRequest().getParameter("currentpage");
		int startindex = (Integer.parseInt(currentpage)-1)*pagesize+1;

		DetachedCriteria d = DetachedCriteria.forClass(Article.class);
		
		ProjectionList pList = Projections.projectionList();  
		pList.add(Projections.property("articleId").as("articleId"));  
		pList.add(Projections.property("articleTitle").as("articleTitle"));  
		pList.add(Projections.property("articlePubTime").as("articlePubTime"));
		
		d.setProjection(pList);  
		d.setResultTransformer(Transformers.aliasToBean(Article.class));
		
		d.add(Restrictions.eq("colId", colId));
		d.addOrder(Order.desc("articleId"));
		List aLists = articleDao.getListByDetachedCriteriaPage(d,startindex,pagesize);

		writeResponseText(Jacksons.me().filter("articleId","articleTitle").readAsString(aLists));
	
	}
	
	public String updateUserInit(){
		
		UserInfo user = (UserInfo) this.getSession().getAttribute(
				"currentUser");
		if ("技术开发部".equals(user.getDepartment())) {
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/**
	 * 更新微信用户信息到表中
	 * 
	 */
	
	public void updateWeChatUser() {
		try {
			UserInfo user = (UserInfo) this.getSession().getAttribute(
					"currentUser");
			if ("quyl".equals(user.getAccount())||"dongj".equals(user.getAccount())) {
				// 调用更新微信用户方法
				try {
					WechatHelper wcHelper = new WechatHelper();
					//获取微信关注用户的基本信息
					if(wcHelper.updateWXUser()){
						actionLoger.log(user, "updateWeChatUser", "更新微信用户信息成功！", getRequest());
						getResponse().getWriter().write("更新成功！");
					}else{
						getResponse().getWriter().write("更新失败！");
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					getResponse().getWriter().write("更新失败！");
				}
			} else {
				getResponse().getWriter().write("无权限操作");
			}
			getResponse().getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	
	public Date getStatetime(int num) throws ParseException{
		  
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		       
		        Calendar c = Calendar.getInstance();  
		        c.add(Calendar.DATE, num);  
		        Date monday = c.getTime();
		        String preMonday = sdf.format(monday);
		        
		        try {
		        	System.out.println("获得时间："+sdf.parse(preMonday));
					return sdf.parse(preMonday);
				} catch (java.text.ParseException e) {
					e.printStackTrace();
					return null;
				}
		   } 
	
	
	/**
	 * 定时任务每周三下午三点
	 * 给备注对应的学校微信用户发送漏洞信息
	 * 给最近一周、未修复的漏洞学校和分公司发送信息
	 * 弃用了真正跑的是定时任务中的 SendMsgTask：sendMsgManager.sendMsg();
	 */
	 
	public void sendMsgToUser(){
		
		UserInfo user = (UserInfo) this.getSession().getAttribute(
				"currentUser");
		
		if ("quyl".equals(user.getAccount())||"dongj".equals(user.getAccount())){
			
			WechatHelper wcHelper = new WechatHelper();
			
			DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);

			d.add(Restrictions.eq("status", "1"));	//未修复
			d.add(Restrictions.ge("modTime", getStatetime(-7)));	//最近一周
			
			d.setProjection(Projections.projectionList()  
			        .add(Projections.min("id"),"id")	//按nameC分组列出对应最小的ID
			        .add(Projections.groupProperty("nameC"))  
				    );  
			d.setResultTransformer(Transformers.aliasToBean(WebLeakInfo.class));
			
			List<WebLeakInfo> leakIds = webLeakInfoDao.getListByDetachedCriteria(d);
			for (WebLeakInfo leakId : leakIds) {
				WebLeakInfo leak = getWebLeakById(leakId.getId());		
				//关注用户备注信息筛选关键字
				String remarkKeyword = leak.getNameC();
				
				//欢迎信息
				String first = leak.getNameC()+" ，您好！";
				//重要程度
				String keyword1 = leak.getScore();
				//事件简介
				String keyword3 = "请注意防范"+leak.getLeakType();
				//时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String keyword2 = sdf.format(leak.getEventDate());
				//备注
				String remark = "漏洞URL "+leak.getUrl();
				//消息详情地址，没有详情页时可以为空
				
				try {
					//对leak.getNameC()学校名进行MD5加密
					String url = "http://"+getIpAddrFormPro()+"/w3sp/showLeakList.do?nameC="+Md5.convertMD5(leak.getNameC());
					//推送信息
					wcHelper.sendTemplateMessage(remarkKeyword, first, keyword1, keyword2,keyword3, remark, url);
					
					//推送信息至学校对应的分公司
					String corpName = getCorpBySchName(remarkKeyword);
					
					if(!"".equals(corpName)){
						//推送信息
						wcHelper.sendTemplateMessage(corpName, first, keyword1, keyword2,keyword3, remark, url);
					}
					//actionLoger.log(user, "sendMsgToUser", "每周一次漏洞信息成功！", getRequest());
				} catch (Exception e) {
					e.printStackTrace();
				}
			 }
		}
	}
	
	public WebLeakInfo getWebLeakById(Long leakId){
		DetachedCriteria dc = DetachedCriteria.forClass(WebLeakInfo.class);
		dc.add(Restrictions.eq("id", leakId));
		List leak = webLeakInfoDao.getListByDetachedCriteria(dc);
		
		if(leak!=null){
			return (WebLeakInfo) leak.get(0);
		}
		return null;
	}
	
	
	
	public List<WebLeakInfo> getWebLeakByNameC(String nameC){
		DetachedCriteria dc = DetachedCriteria.forClass(WebLeakInfo.class);
		dc.add(Restrictions.eq("nameC", nameC));
		dc.add(Restrictions.eq("status", "1"));	//未修复
		dc.add(Restrictions.ge("modTime", getStatetime(-7)));	//最近一周
		List<WebLeakInfo> leak = webLeakInfoDao.getListByDetachedCriteria(dc);
		
		if(leak!=null){
			return leak;
		}
		return null;
	}
	
	/*
	 * 展示最近一周的漏洞信息
	 * 
	 * */
	
	public String showLeakList(){
		
		String nameC = getRequest().getParameter("nameC");
		if(nameC==null||"".equals(nameC)){
			return null;
		}
		nameC = Md5.convertMD5(nameC);//解密
		DetachedCriteria dc = DetachedCriteria.forClass(WebLeakInfo.class);
		dc.add(Restrictions.like("nameC", "%"+nameC+"%"));
		dc.add(Restrictions.eq("status", "1"));	//未修复
		dc.add(Restrictions.ge("modTime", getStatetime(-7)));	//最近一周
		List<WebLeakInfo> leak = webLeakInfoDao.getListByDetachedCriteria(dc);
		
		getRequest().setAttribute("leaks", leak);
		
		return SUCCESS;
		
	}
	
	/*
	 * 展示最近一周的漏洞信息（特定漏洞类型的漏洞信息）
	 * 
	 * */
	
	public String showLeakForLeakType(){
		
		String nameC = getRequest().getParameter("nameC");
		nameC = Md5.convertMD5(nameC);//解密
		DetachedCriteria dc = DetachedCriteria.forClass(WebLeakInfo.class);
		dc.add(Restrictions.like("nameC", "%"+nameC+"%"));
		dc.add(Restrictions.eq("status", "1"));	//未修复
		dc.add(Restrictions.ge("modTime", getStatetime(-7)));	//最近一周
		dc.add(Restrictions.or(Restrictions.like("leakType", "%后门%")
				,Restrictions.or(Restrictions.eq("leakType", "黑页")
						,Restrictions.eq("leakType", "弱口令漏洞"))));
		List<WebLeakInfo> leak = webLeakInfoDao.getListByDetachedCriteria(dc);
		
		getRequest().setAttribute("leaks", leak);
		
		return SUCCESS;
		
	}
	
	/*
	 * 手动发送单条漏洞信息
	 * 
	 * */
	
	public String sendSingleMsg() {

		UserInfo user = (UserInfo) this.getSession()
				.getAttribute("currentUser");

		if ("quyl".equals(user.getAccount())|| "dongj".equals(user.getAccount())) {

			String remarkKeyword = getRequest().getParameter("remarkKeyword");
			String nameC = getRequest().getParameter("nameC");
			String score = getRequest().getParameter("score");
			String leakType = getRequest().getParameter("leakType");
			String leakUrl = getRequest().getParameter("leakUrl");
			String leakTime = getRequest().getParameter("leakTime");

			String msgs = "发送成功！";

			if ("".equals(remarkKeyword) || remarkKeyword == null) {
				msgs = "微信备注不能为空";
				getRequest().setAttribute("msgs", msgs);
				return ERROR;
			}
			if ("".equals(nameC) || nameC == null) {
				msgs = "学校名不能为空";
				getRequest().setAttribute("msgs", msgs);
				return ERROR;
			}
			if ("".equals(score) || score == null) {
				msgs = "重要程度不能为空";
				getRequest().setAttribute("msgs", msgs);
				return ERROR;
			}
			if ("".equals(leakType) || leakType == null) {
				msgs = "漏洞类型不能为空";
				getRequest().setAttribute("msgs", msgs);
				return ERROR;
			}
			if ("".equals(leakUrl) || leakUrl == null) {
				msgs = "漏洞URL不能为空";
				getRequest().setAttribute("msgs", msgs);
				return ERROR;
			}
			if ("".equals(leakTime) || leakTime == null) {
				msgs = "发生时间不能为空";
				getRequest().setAttribute("msgs", msgs);
				return ERROR;
			}

			WechatHelper wcHelper = new WechatHelper();
			String sendMsg = "";
			sendMsg=wcHelper.sendTemplateMessage(remarkKeyword, nameC + " ，您好！", score,
					leakTime, "请注意防范" + leakType, "漏洞URL " + leakUrl, "");
			
			//推送信息至学校对应的分公司
			String corpName = getCorpBySchName(remarkKeyword);
			
			if(!"".equals(corpName)){
				//推送信息
				wcHelper.sendTemplateMessage(corpName, corpName + " ，您好！", score,
						leakTime, "请注意防范" + leakType, "漏洞URL " + leakUrl, "");
			}
			//单条推送信息入库存WEBLEAK_PUSH_MONITOR表
			if ("SUC".equals(sendMsg)) {
				LeakPushMonitor obj=new LeakPushMonitor();
				obj.setNameC(nameC);
				obj.setCorpName(corpName);
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					obj.setLastTime(dateFormat.parse(leakTime));
				} catch (java.text.ParseException e) {
						e.printStackTrace();
				}
				obj.setLeakDetail(leakUrl);
				obj.setLeakType(leakType);
				obj.setProName("自定义消息推送");
				obj.setStatus("365");
				leakPushMonitorManager.save(obj);
			}
			actionLoger.log(user, "sendSingleMsg", "推送单条自定义漏洞信息！",getRequest());
			getRequest().setAttribute("msgs", msgs);
			return SUCCESS;

		}

		return ERROR;
	}
	
	public String sendMsgInit(){
		return SUCCESS;
	}

	
	public Boolean canSendMsgForOneDay(String dateTime){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DetachedCriteria d = DetachedCriteria.forClass(Actionlog.class);
		d.add(Restrictions.eq("action", "sendMsgForLeakType"));
		d.setProjection(Projections.projectionList()
				.add(Projections.max("id"),"id")
				.add(Projections.max("time").as("time")));  
		d.setResultTransformer(Transformers.aliasToBean(Actionlog.class));
		List<Actionlog> logs = actionlogManager.getListByDetachedCriteria(d);
		
		for (Actionlog actionlog : logs) {
			if(!"".equals(actionlog.getTime())&&actionlog.getTime()!=null){
				String time = sdf.format(actionlog.getTime());
				if(dateTime.equals(time)){
					return false;
				}
			}
		}
		
		return true;
	}
	
	public String getIpAddrFormPro(){
		String DEFAULT_FILENAME = "wechat.properties";
		Properties prop = new Properties();
		try {
			InputStream is = ArticleAction.class.getClassLoader()
					.getResourceAsStream(DEFAULT_FILENAME);
			if (is == null) {
				throw new IOException();
			}
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		 String ipAddr = prop.getProperty("ipAddr");
		 return ipAddr;
	}
	
	
	/*
	 * 针对特定漏洞类型，每天手动触发
	 * */
	
	public void sendMsgForLeakType(){
		
		try {
			SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd");
			if(!canSendMsgForOneDay(sdfs.format(getStatetime(0)))){//如果当天已经发送过，则无法发送
				getResponse().getWriter().write("Can only be sent once a day!");
				getResponse().getWriter().close();
			}else{
				UserInfo user = (UserInfo) this.getSession().getAttribute(
						"currentUser");
				if ("quyl".equals(user.getAccount())||"dongj".equals(user.getAccount())) {
					// 调用更新微信用户方法
					try {	
						WechatHelper wcHelper = new WechatHelper();
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						
						DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
						d.add(Restrictions.eq("status", "1"));	//未修复
						//System.out.println("测试:"+sdf.parse(getStatetime(0)));
						d.add(Restrictions.ge("modTime", getStatetime(0)));	//当天
						d.add(Restrictions.or(Restrictions.like("leakType", "%后门%")
								,Restrictions.or(Restrictions.eq("leakType", "黑页")
										,Restrictions.eq("leakType", "弱口令漏洞"))));
						
						d.setProjection(Projections.projectionList()  
						        .add(Projections.min("id"),"id")
						        .add(Projections.groupProperty("nameC"))  
							    );  
						d.setResultTransformer(Transformers.aliasToBean(WebLeakInfo.class));  
						
						List<WebLeakInfo> leakIds = webLeakInfoDao.getListByDetachedCriteria(d);
						for (WebLeakInfo leakId : leakIds) {
							WebLeakInfo leak = getWebLeakById(leakId.getId());		
							//关注用户备注信息筛选关键字
							String remarkKeyword = leak.getNameC();
							
							//欢迎信息
							String first = leak.getNameC()+" ，您好！";
							//重要程度
							String keyword1 = leak.getScore();
							//事件简介
							String keyword3 = "请注意防范"+leak.getLeakType();
							//时间
							
							String keyword2 = sdf.format(leak.getEventDate());
							//备注
							String remark = "漏洞URL "+leak.getUrl();
							//消息详情地址，没有详情页时可以为空
							
							String url = "http://"+getIpAddrFormPro()+"/w3sp/showLeakForLeakType.do?nameC="+Md5.convertMD5(leak.getNameC());
							//推送信息
							String sendMsg = "";
							sendMsg=wcHelper.sendTemplateMessage(remarkKeyword, first, keyword1, keyword2,keyword3, remark, url);
							
							//推送信息至学校对应的分公司
							String corpName = getCorpBySchName(remarkKeyword);
							
							if(!"".equals(corpName)){
								//推送信息
								wcHelper.sendTemplateMessage(corpName, first, keyword1, keyword2,keyword3, remark, url);
							}
							//针对特定漏洞类型，每天手动触发信息入库存WEBLEAK_PUSH_MONITOR表
							if ("SUC".equals(sendMsg)) {
								LeakPushMonitor obj=new LeakPushMonitor();
								obj.setNameC( leak.getNameC());
								obj.setCorpName(corpName);
								try {
									SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
									String strDate = dateFormat.format(new Date());
									obj.setLastTime(dateFormat.parse(strDate));
								} catch (java.text.ParseException e) {
										e.printStackTrace();
								}
								obj.setLeakDetail(leak.getUrl());
								obj.setLeakType(leak.getLeakType());
								obj.setProName("特定漏洞类型手动推送");
								obj.setStatus("7");
								leakPushMonitorManager.save(obj);
							}
						}
						actionLoger.log(user, "sendMsgForLeakType", "当天漏洞类型为后门、黑页、弱口令漏洞信息推送成功！",getRequest());
						getResponse().getWriter().write("success!");

					} catch (Exception e) {
						e.printStackTrace();
						getResponse().getWriter().write("error!");
					}
				} else {
					getResponse().getWriter().write("no right!");
				}
				getResponse().getWriter().close();
			 }
			} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	
	}
	/*
	 * 漏洞信息菜单
	 *  */
	public String leakMsgMenu(){
		
		try {
			UserInfo user = (UserInfo) this.getSession().getAttribute(
					"currentUser");
			if ("quyl".equals(user.getAccount())||"dongj".equals(user.getAccount())) {
				return SUCCESS;
				}
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
		return null;
		
	}

/*
 * 通过学校获取分公司名
 * @param schName //学校名
 * @return corpName //分公司名
 * */
	public String getCorpBySchName(String schName){
		
		String corpName = "";
		DetachedCriteria d = DetachedCriteria.forClass(WebLeakInfo.class);
		d.add(Restrictions.like("nameC", "%"+schName+"%"));	//未修复
		List<WebLeakInfo> corpNamelist = webLeakInfoManager.getListByDetachedCriteria(d);
		if(corpNamelist.size()!=0){
			corpName = corpNamelist.get(0).getCorpSubName();
		}
		return corpName;
	}
		
}
