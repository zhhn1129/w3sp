package com.cernet.utils.log;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.cernet.model.UserInfo;
import com.cernet.web.action.BaseAction;

public class Log extends BaseAction{ 
	private ActionlogManager actionlogManager;
	
	/**
	 * 
	 * @param currentUser 当前用户
	 * @param action 登录login,登出,logout, 新增，如:A_contact,删除D_contact,查询S_contact,修改E_contact
	 * @param desc 相关动作的具体描述 如，新增联系人
	 * 
	 *
	 */
	public void log(UserInfo currentUser, String action, String desc, HttpServletRequest request){
		Actionlog aLog = new Actionlog();
		if(null != currentUser && null != currentUser.getAccount()
			&& !"".equals(currentUser.getAccount())){
			aLog.setUsername(currentUser.getAccount());
		}else{
			aLog.setUsername("匿名用户");
			
		}
		
		aLog.setAction(action);
		aLog.setDescription(desc);
		aLog.setUserIp(request.getRemoteAddr());
		aLog.setLocation(request.getRequestURI());
		aLog.setTime(new Date());
		actionlogManager.save(aLog);
		
	}
	
	public void logForTask(String username, String action, String desc){
		Actionlog aLog = new Actionlog();

		aLog.setUsername(username);

		aLog.setAction(action);
		aLog.setDescription(desc);
		aLog.setUserIp("本机");
		aLog.setLocation("本机");
		aLog.setTime(new Date());
		actionlogManager.save(aLog);
		
	}
	

	public void setActionlogManager(ActionlogManager actionlogManager) {
		this.actionlogManager = actionlogManager;
	}
	
	public String logInfo() {
		
			plist = actionlogManager.getListByDetachedCriteriaPage(page, pagesize,
					sort, dir);
			return SUCCESS;
		}
}
