package com.cernet.web.action;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.cernet.cwui.util.HSSFExcelHandler;
import com.cernet.model.UserInfo;
import com.cernet.model.WebLeakNView;
import com.cernet.model.WebleakAxisView;
import com.cernet.service.AxisViewManager;
import com.cernet.service.DataDictManager;
import com.cernet.service.HandleLastManager;
import com.cernet.service.WebLeakNViewManager;

public class TestResultAction extends BaseAction {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private WebLeakNViewManager webLeakNViewManager;
	private WebLeakNView nodeView;
	private AxisViewManager axisViewManager;
	private WebleakAxisView axisView;
	private HandleLastManager handleLastManager;
	private String corpSubName;
	private String corpName;

	
	private List corpSubList;
	private List<WebLeakNView> nodeList;
	private List<WebleakAxisView> axisList;
	private String lastFriday;
	private Object infoSum;
	private DataDictManager dataDictManager;
	private int axisCount;
	
	private String _3rdFriday;
	private String _4thFriday;
	private String _5thFriday;
	private String _6thFriday;
	private String _7thFriday;
	private String _8thFriday;
	private String _2cdFriday;
	private String[] attrs;
	private String[] captions;
	

	public Object getInfoSum() {
		return infoSum;
	}


	public void setCorpSubList(List corpSubList) {
		this.corpSubList = corpSubList;
	}

	public List getCorpSubList() {
		return corpSubList;
	}

	public String getCorpSubName() {
		return corpSubName;
	}

	public void setCorpSubName(String corpSubName) {
		this.corpSubName = corpSubName;
	}

	public void setDataDictManager(DataDictManager dataDictManager) {
		this.dataDictManager = dataDictManager;
	}


	public String nodeResult() {
		UserInfo user = (UserInfo)this.getSession().getAttribute("currentUser");
		if("技术开发部".equals(user.getDepartment())){			//添加分公司查看权限
			DetachedCriteria queryCriteria = DetachedCriteria
			.forClass(WebLeakNView.class);
			queryCriteria.addOrder(Order.desc("statusERR"));
			nodeList = webLeakNViewManager.getListByDetachedCriteria(queryCriteria);
			
			DetachedCriteria queryCriteriaSum = DetachedCriteria
			.forClass(WebLeakNView.class);
			ProjectionList pl = Projections.projectionList();
			pl.add(Projections.alias(Projections.sum("statusRPR"), "statusRPR"))
					.add(
							Projections.alias(Projections.sum("statusCLS"),
									"statusCLS")).add(
							Projections.alias(Projections.sum("statusLNK"),
									"statusLNK")).add(
							Projections.alias(Projections.sum("statusERR"),
									"statusERR")).add(
							Projections.alias(Projections.sum("statusSum"),
									"statusSum"));

			infoSum = webLeakNViewManager.getListByDetachedCriteria(queryCriteriaSum
					.setProjection(pl));
			Object[] infoSumObj = (Object[]) ((List) infoSum).get(0);
			getRequest()
					.setAttribute(
							"statusProSum",
							new DecimalFormat("0.00")
									.format(((Long) infoSumObj[3] * 100.0 / (Long) infoSumObj[4]))+"%");
			
			
		}else {
			DetachedCriteria queryCriteria = DetachedCriteria
			.forClass(WebLeakNView.class);
			queryCriteria.add(Restrictions.eq("corpSubName", user.getDepartment()));//添加按分公司查询条件
			
			queryCriteria.addOrder(Order.desc("statusERR"));
			nodeList = webLeakNViewManager.getListByDetachedCriteria(queryCriteria);
			
			DetachedCriteria queryCriteriaSum = DetachedCriteria
			.forClass(WebLeakNView.class);
			queryCriteriaSum.add(Restrictions.eq("corpSubName", user.getDepartment()));//添加按分公司查询条件

			
			ProjectionList pl = Projections.projectionList();
			pl.add(Projections.alias(Projections.sum("statusRPR"), "statusRPR"))
					.add(
							Projections.alias(Projections.sum("statusCLS"),
									"statusCLS")).add(
							Projections.alias(Projections.sum("statusLNK"),
									"statusLNK")).add(
							Projections.alias(Projections.sum("statusERR"),
									"statusERR")).add(
							Projections.alias(Projections.sum("statusSum"),
									"statusSum"));

			infoSum = webLeakNViewManager.getListByDetachedCriteria(queryCriteriaSum
					.setProjection(pl));
			Object[] infoSumObj = (Object[]) ((List) infoSum).get(0);
			getRequest()
					.setAttribute(
							"statusProSum",
							new DecimalFormat("0.00")
									.format(((Long) infoSumObj[3] * 100.0 / (Long) infoSumObj[4]))+"%");
			
		} 
		
		String exportExcel = getRequest().getParameter("exportExcel");//导出表格
		if(exportExcel!=null && !"".equals(exportExcel)){
				HSSFExcelHandler hSSFExcelHandler = new HSSFExcelHandler(getResponse());
				hSSFExcelHandler.setObjects(nodeList);
				hSSFExcelHandler.setObjClass(WebLeakNView.class);
				String[] attrs = {"nameC","corpSubName","statusRPR","statusCLS","statusLNK","statusERR","statusSum","statusPro"};
				String[] captions = {"学校","分公司","已修复","链接打不开","网络不通","未处理","合计","未处理漏洞占比"};
				
				hSSFExcelHandler.init("按学校统计报表", captions, attrs, nodeList,
						WebLeakNView.class);

				hSSFExcelHandler.setAttrs(attrs);
				hSSFExcelHandler.setCaptions(captions);
				getRequest().setAttribute("hSSFExcelHandler",hSSFExcelHandler);
				
				return "xls";
			}
		
		return SUCCESS;
	}


	public WebLeakNView getNodeView() {
		return nodeView;
	}

	public void setNodeView(WebLeakNView nodeView) {
		this.nodeView = nodeView;
	}

	public String axisResult() {

		
		getRequest().setAttribute("axisCount", axisCount);
		
		UserInfo user = (UserInfo)this.getSession().getAttribute("currentUser");
		if("技术开发部".equals(user.getDepartment())){			//添加分公司查看权限
			corpSubList = dataDictManager.getDataDictByParams("W3SP_WEBLEAK",
			"CORP_SUB_NAME"); // 获取分公司
			DetachedCriteria queryCriteria = DetachedCriteria
			.forClass(WebleakAxisView.class);
			if (corpSubName != null && !"".equals(corpSubName)) {
				queryCriteria.add(Restrictions.eq("corpSubName", corpSubName));
			}else{
				queryCriteria.add(Restrictions.eq("corpSubName","北京分公司"));
			}
			axisList = axisViewManager.getListByDetachedCriteria(queryCriteria);
		}else {
			DetachedCriteria queryCriteria = DetachedCriteria
			.forClass(WebleakAxisView.class);
			queryCriteria.add(Restrictions.eq("corpSubName", user.getDepartment()));
			corpName=user.getDepartment();
			corpSubList = dataDictManager.getDataDictByParams("W3SP_WEBLEAK",
			user.getDepartment()); // 获取分公司
			axisList = axisViewManager.getListByDetachedCriteria(queryCriteria);
		}
		
		Date lastFridayDate = handleLastManager.getLastFriday();
		DateFormat df = new SimpleDateFormat("yyyy-M-d");
		lastFriday = df.format(lastFridayDate);
		Calendar c = Calendar.getInstance();
		c.setTime(lastFridayDate);
		c.add(Calendar.DATE, -7);
		Date _2cdFridayDate = c.getTime();
		_2cdFriday = df.format(_2cdFridayDate);
		c.add(Calendar.DATE, -7);
		Date _3rdFridayDate = c.getTime();
		_3rdFriday = df.format(_3rdFridayDate);
		c.add(Calendar.DATE, -7);
		Date _4thFridayDate = c.getTime();
		_4thFriday = df.format(_4thFridayDate);
		c.add(Calendar.DATE, -7);
		Date _5thFridayDate = c.getTime();
		_5thFriday = df.format(_5thFridayDate);
		c.add(Calendar.DATE, -7);
		Date _6thFridayDate = c.getTime();
		_6thFriday = df.format(_6thFridayDate);
		c.add(Calendar.DATE, -7);
		Date _7thFridayDate = c.getTime();
		_7thFriday = df.format(_7thFridayDate);
		c.add(Calendar.DATE, -7);
		Date _8thFridayDate = c.getTime();
		_8thFriday = df.format(_8thFridayDate);

		String exportExcel = getRequest().getParameter("exportExcel");//导出表格
		if(exportExcel!=null && !"".equals(exportExcel)){
				HSSFExcelHandler hSSFExcelHandler = new HSSFExcelHandler(getResponse());
				hSSFExcelHandler.setObjects(axisList);
				hSSFExcelHandler.setObjClass(WebleakAxisView.class);
				
				if(axisCount==1){
					String[] attrs= {"status1","nodeId","nameC","corpSubName","url","leakType","eventDateToString"}; String[] captions= {lastFriday,"学校编号","学校 ","分公司","URL","漏洞类型","事件时间"};
					setAttrs(attrs);setCaptions(captions);
				}else if(axisCount==2){
					String[] attrs= {"status2","status1","nodeId","nameC","corpSubName","url","leakType","eventDateToString"}; String[] captions= {_2cdFriday,lastFriday,"学校编号","学校 ","分公司","URL","漏洞类型","事件时间"};
					setAttrs(attrs);setCaptions(captions);
				}else if(axisCount==3){
					String[] attrs= {"status3","status2","status1","nodeId","nameC","corpSubName","url","leakType","eventDateToString"}; String[] captions= {_3rdFriday,_2cdFriday,lastFriday,"学校编号","学校 ","分公司","URL","漏洞类型","事件时间"};
					setAttrs(attrs);setCaptions(captions);
				}else if(axisCount==4){
					String[] attrs= {"status4","status3","status2","status1","nodeId","nameC","corpSubName","url","leakType","eventDateToString"}; String[] captions= {_4thFriday,_3rdFriday,_2cdFriday,lastFriday,"学校编号","学校 ","分公司","URL","漏洞类型","事件时间"};
					setAttrs(attrs);setCaptions(captions);
				}else if(axisCount==5){
					String[] attrs= {"status5","status4","status3","status2","status1","nodeId","nameC","corpSubName","url","leakType","eventDateToString"}; String[] captions= {_5thFriday,_4thFriday,_3rdFriday,_2cdFriday,lastFriday,"学校编号","学校 ","分公司","URL","漏洞类型","事件时间"};
					setAttrs(attrs);setCaptions(captions);
				}else if(axisCount==6){
					String[] attrs= {"status6","status5","status4","status3","status2","status1","nodeId","nameC","corpSubName","url","leakType","eventDateToString"}; String[] captions= {_6thFriday,_5thFriday,_4thFriday,_3rdFriday,_2cdFriday,lastFriday,"学校编号","学校 ","分公司","URL","漏洞类型","事件时间"};
					setAttrs(attrs);setCaptions(captions);
				}else if(axisCount==7){
					String[] attrs= {"status7","status6","status5","status4","status3","status2","status1","nodeId","nameC","corpSubName","url","leakType","eventDateToString"}; String[] captions= {_7thFriday,_6thFriday,_5thFriday,_4thFriday,_3rdFriday,_2cdFriday,lastFriday,"学校编号","学校 ","分公司","URL","漏洞类型","事件时间"};
					setAttrs(attrs);setCaptions(captions);
				}else if(axisCount==8){
					String[] attrs= {"status8","status7","status6","status5","status4","status3","status2","status1","nodeId","nameC","corpSubName","url","leakType","eventDateToString"}; String[] captions= {_8thFriday,_7thFriday,_6thFriday,_5thFriday,_4thFriday,_3rdFriday,_2cdFriday,lastFriday,"学校编号","学校 ","分公司","URL","漏洞类型","事件时间"};
					setAttrs(attrs);setCaptions(captions);
				}else if(axisCount==0){
					String[] attrs= {"nodeId","nameC","corpSubName","url","leakType","eventDateToString"}; String[] captions= {"学校编号","学校 ","分公司","URL","漏洞类型","事件时间"};
					setAttrs(attrs);setCaptions(captions);
				}
				hSSFExcelHandler.init("漏洞追踪统计报表 ", captions, attrs, axisList,
						WebleakAxisView.class);
				hSSFExcelHandler.setAttrs(attrs);
				hSSFExcelHandler.setCaptions(captions);
				
				getRequest().setAttribute("hSSFExcelHandler",hSSFExcelHandler);
				
				return "xls";
			}
		return SUCCESS;
	}

	public AxisViewManager getAxisViewManager() {
		return axisViewManager;
	}

	public void setAxisViewManager(AxisViewManager axisViewManager) {
		this.axisViewManager = axisViewManager;
	}

	public WebleakAxisView getAxisView() {
		return axisView;
	}

	public void setAxisView(WebleakAxisView axisView) {
		this.axisView = axisView;
	}

	public List<WebLeakNView> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<WebLeakNView> nodeList) {
		this.nodeList = nodeList;
	}

	public int getAxisCount() {
		return axisCount;
	}

	public void setAxisCount(int axisCount) {
		this.axisCount = axisCount;
	}

	public List<WebleakAxisView> getAxisList() {
		return axisList;
	}

	public void setAxisList(List<WebleakAxisView> axisList) {
		this.axisList = axisList;
	}

	public HandleLastManager getHandleLastManager() {
		return handleLastManager;
	}

	public void setHandleLastManager(HandleLastManager handleLastManager) {
		this.handleLastManager = handleLastManager;
	}

	public String getLastFriday() {
		return lastFriday;
	}

	public void setLastFriday(String lastFriday) {
		this.lastFriday = lastFriday;
	}

	public String get_3rdFriday() {
		return _3rdFriday;
	}

	public void set_3rdFriday(String _3rdFriday) {
		this._3rdFriday = _3rdFriday;
	}

	public String get_4thFriday() {
		return _4thFriday;
	}

	public void set_4thFriday(String _4thFriday) {
		this._4thFriday = _4thFriday;
	}

	public String get_5thFriday() {
		return _5thFriday;
	}

	public void set_5thFriday(String _5thFriday) {
		this._5thFriday = _5thFriday;
	}

	public String get_6thFriday() {
		return _6thFriday;
	}

	public void set_6thFriday(String _6thFriday) {
		this._6thFriday = _6thFriday;
	}

	public String get_7thFriday() {
		return _7thFriday;
	}

	public void set_7thFriday(String _7thFriday) {
		this._7thFriday = _7thFriday;
	}

	public String get_8thFriday() {
		return _8thFriday;
	}

	public void set_8thFriday(String _8thFriday) {
		this._8thFriday = _8thFriday;
	}
	
	public String get_2cdFriday() {
		return _2cdFriday;
	}
	
	public void set_2cdFriday(String _2cdFriday) {
		this._2cdFriday = _2cdFriday;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}


	public WebLeakNViewManager getWebLeakNViewManager() {
		return webLeakNViewManager;
	}


	public void setWebLeakNViewManager(WebLeakNViewManager webLeakNViewManager) {
		this.webLeakNViewManager = webLeakNViewManager;
	}


	public String[] getAttrs() {
		return attrs;
	}


	public void setAttrs(String[] attrs) {
		this.attrs = attrs;
	}


	public String[] getCaptions() {
		return captions;
	}


	public void setCaptions(String[] captions) {
		this.captions = captions;
	}

}
