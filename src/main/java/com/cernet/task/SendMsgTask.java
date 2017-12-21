package com.cernet.task;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cernet.service.SendMsgManager;
import com.cernet.utils.log.Actionlog;
import com.cernet.utils.log.ActionlogManager;
import com.cernet.utils.log.Log;

public class SendMsgTask extends QuartzJobBean{

	private SendMsgManager sendMsgManager;
	private Log actionLoger;
	
	public Log getActionLoger() {
		return actionLoger;
	}

	public void setActionLoger(Log actionLoger) {
		this.actionLoger = actionLoger;
	}

	public SendMsgManager getSendMsgManager() {
		return sendMsgManager;
	}

	public void setSendMsgManager(SendMsgManager sendMsgManager) {
		this.sendMsgManager = sendMsgManager;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			System.out.println("===========" + new Date()
					+ ":sendMsg start ==============");
			sendMsgManager.sendMsg();
			actionLoger.logForTask("定时任务", "SendMsgTask", "定时任务每周一次推送信息成功！");
			System.out.println("===========" + new Date()
					+ ":sendMsg end ==============");
		} catch (Exception e) {
			e.printStackTrace();
			throw new JobExecutionException();
		}
	}
}
