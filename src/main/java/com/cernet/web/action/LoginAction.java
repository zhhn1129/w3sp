/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cernet.web.action;

import java.io.IOException;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cernet.model.UserInfo;
import com.cernet.service.LoginManager;
import com.cernet.util.PasswordUtil;


/**
 *
 * @author Administrator
 */
public class LoginAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LoginManager loginManager;
	private String message ;		
	
	public String login() throws Exception {
    	String username = getRequest().getParameter("username");
    	String passwd = getRequest().getParameter("passwd");
        if(username != null && !"".equals(username) && passwd != null && !"".equals(passwd)){
        	UserInfo currentUser = null;
        	DetachedCriteria queryCriteria = DetachedCriteria.forClass(UserInfo.class);
    		queryCriteria.add(Restrictions.eq("account", username));
    		List<UserInfo> list = loginManager.getListByDetachedCriteria(queryCriteria);
    		if(list != null && list.size() != 0){
    			currentUser = list.get(0);
    		} 
        	//判断用户名与密码是否正确，如果 正确则
        	if(currentUser != null&& currentUser.getEncPw().trim().equals(PasswordUtil.encrypt(passwd, currentUser.getKey().trim()))){
        		getSession().setAttribute("username", username);
            	getSession().setAttribute("passwd", passwd);
            	getSession().setAttribute("contact", currentUser.getContact());
            	getSession().setAttribute("currentUser", currentUser);
            	actionLoger.log(currentUser, "LoginAction", "Login", getRequest());
            	return SUCCESS;
            	
        	}else{
        		message = "用户名与密码不正确";
        		return ERROR;
        	}
        } else {
//        	message = "用户名与密码不能为空";
        	message = "";
            return ERROR;
        }
    }
	
	public String loginOut(){
		getSession().removeAttribute("username");
		getSession().removeAttribute("passwd");
		getSession().removeAttribute("contact");
		getSession().removeAttribute("localuser");
		return SUCCESS;
	}
	
	//set and get method
	
	public LoginManager getLoginManager() {
		return loginManager;
	}

	public void setLoginManager(LoginManager loginManager) {
		this.loginManager = loginManager;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String changePWD(){
		return SUCCESS;
	}
	
	public String savePWD(){
		String oldPWD = this.getRequest().getParameter("oldPWD");
		String newPWD = this.getRequest().getParameter("newPWD");
		String renewPWD = this.getRequest().getParameter("renewPWD");
		UserInfo user = (UserInfo)this.getSession().getAttribute("currentUser");
		if(oldPWD == null || "".equals(oldPWD)){
			return ERROR;
		}
		String oldPassword = PasswordUtil.encrypt(oldPWD, user.getKey());
		if(!user.getEncPw().equals(oldPassword)){
			return ERROR;
		}
		
		
		if(newPWD != null && !"".equals(newPWD) && renewPWD != null && !"".equals(renewPWD)
			&& renewPWD.equals(newPWD)){
			
			String password = PasswordUtil.encrypt(newPWD, user.getKey());
			user.setEncPw(password);
			loginManager.save(user);
		}
		this.getSession().removeAttribute("username");
		return SUCCESS;
	}
	
	public void checkOldPWD(){
		String oldPWD = this.getRequest().getParameter("oldPWD");
		if(oldPWD == null || "".equals(oldPWD)){
			try {
				getResponse().getWriter().write("wrong");
				getResponse().getWriter().close();
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		UserInfo user = (UserInfo)this.getSession().getAttribute("currentUser");
		String oldPassword = PasswordUtil.encrypt(oldPWD, user.getKey());
		if(user.getEncPw().equals(oldPassword)){
			try {
				getResponse().getWriter().write("right");
				getResponse().getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			try {
				getResponse().getWriter().write("wrong");
				getResponse().getWriter().close();
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
