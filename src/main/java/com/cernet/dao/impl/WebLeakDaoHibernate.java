package com.cernet.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.cernet.dao.WebLeakDao;
import com.cernet.model.UserInfo;
import com.cernet.model.WebLeak;
import com.cernet.model.WebLeakModel;

public class WebLeakDaoHibernate extends GenericDaoHibernate<WebLeak, Long>
		implements WebLeakDao {

	
	
	
	/**
	 * 
	 
	public List getWebLeakModelList(final Map<String, Object> paraMap) {

		 return this.getHibernateTemplate().executeFind(new HibernateCallback(){
				public List doInHibernate(Session session) throws HibernateException, SQLException {   
					final List<WebLeakModel> modelList = new ArrayList<WebLeakModel>();
					WebLeakModel model ;
					String sql = "";
					UserInfo user = (UserInfo)paraMap.get("user");
					if("技术开发部".equals(user.getDepartment())){
						sql = "select t.corp_sub_name as subName, count(t.ctrled_tag) as ctrledNum, count(t.url) as urlNum, count(t.path) as pathNum, MAX(t.event_date) as lastEventDate from w3sp_webleak t group by t.corp_sub_name order by t.corp_sub_name";
					}else{
						sql = "select t.corp_sub_name as subName, count(t.ctrled_tag) as ctrledNum, count(t.url) as urlNum, count(t.path) as pathNum, MAX(t.event_date) as lastEventDate from w3sp_webleak t where t.corp_sub_name = '" + user.getDepartment() + "' group by t.corp_sub_name order by t.corp_sub_name";
					}
	                
	               List webLeakList = session.createSQLQuery(sql).list();
	               
	               for(int i = 0; i < webLeakList.size();i++){
	            	   model = new WebLeakModel();
	            	   Object[] webLeakArray = (Object[])webLeakList.get(i);
	            	   model.setId(i+1);
	            	   model.setCorpSubName((String)webLeakArray[0]);
	            	   model.setCtrledNum(Long.parseLong(webLeakArray[1].toString()));
	            	   model.setUrlNum(Long.parseLong(webLeakArray[2].toString()));
	            	   model.setPathNum(Long.parseLong(webLeakArray[3].toString()));
	            	   model.setLastEventDate((Date) webLeakArray[4]);
	            	   
	            	   modelList.add(model);
	               }
	               
	               
	                return modelList;
	            }
	         });
		
		

	}
	*/
	
	/**
	 * 
	 
	public List getWebLeakModelListByCorpName(final String cormName) {
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public List doInHibernate(Session session) throws HibernateException, SQLException {   
				final List<WebLeakModel> modelList = new ArrayList<WebLeakModel>();
				WebLeakModel model ;
                String sql = "select a.name_c as nameC, count(a.ctrled_tag) as ctrNum, count(a.url) as urlNum, count(a.path) as pathNum, MAX(a.event_date) as lastEventDate from (select * from w3sp_webleak t where t.corp_sub_name = :corpSubName) a group by a.name_c";
                SQLQuery q = session.createSQLQuery(sql);
                q.setString("corpSubName",cormName);
                List webLeakList = q.list();
               
               for(int i = 0; i < webLeakList.size();i++){
            	   model = new WebLeakModel();
            	   Object[] webLeakArray = (Object[])webLeakList.get(i);
            	   model.setId(i+1);
            	   model.setNameC((String)webLeakArray[0]);
            	   model.setCorpSubName(cormName);
            	   model.setCtrledNum(Long.parseLong(webLeakArray[1].toString()));
            	   model.setUrlNum(Long.parseLong(webLeakArray[2].toString()));
            	   model.setPathNum(Long.parseLong(webLeakArray[3].toString()));
            	   model.setLastEventDate((Date) webLeakArray[4]);
            	   
            	   modelList.add(model);
               }
               
               
                return modelList;
            }
         });
	}

	*/
public List getWebLeakModelListByNameC(final String nameC) {
		
		return this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public List doInHibernate(Session session) throws HibernateException, SQLException {   
				final List<WebLeakModel> modelList = new ArrayList<WebLeakModel>();
				WebLeakModel model ;
                String sql = "select t.ctrled_tag,count(t.url),count(t.path),count(t.ctrled_tag) from w3sp_webleak t where t.name_c = :nameC group by t.ctrled_tag";
                SQLQuery q = session.createSQLQuery(sql);
                q.setString("nameC",nameC);
                List webLeakList = q.list();
               
               for(int i = 0; i < webLeakList.size();i++){
            	   model = new WebLeakModel();
            	   Object[] webLeakArray = (Object[])webLeakList.get(i);
            	   model.setId(i+1);
            	   model.setCorpSubName((String)webLeakArray[0]);
            	   model.setCtrledNum(Long.parseLong(webLeakArray[1].toString()));
            	   model.setUrlNum(Long.parseLong(webLeakArray[2].toString()));
            	   model.setPathNum(Long.parseLong(webLeakArray[3].toString()));
            	   modelList.add(model);
               }
               
               
                return modelList;
            }
         });
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
