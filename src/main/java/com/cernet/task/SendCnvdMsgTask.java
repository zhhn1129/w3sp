package com.cernet.task;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cernet.utils.log.Log;
import com.cernet.web.action.GetCnvdMsgAction;
import com.cernet.web.action.HighPushAction;

public class SendCnvdMsgTask extends QuartzJobBean{

	private Log actionLoger;
	private HighPushAction highPusher;
	private GetCnvdMsgAction cnvdMsger;
	
	public Log getActionLoger() {
		return actionLoger;
	}

	public void setActionLoger(Log actionLoger) {
		this.actionLoger = actionLoger;
	}

	public HighPushAction getHighPusher() {
		return highPusher;
	}

	public void setHighPusher(HighPushAction highPusher) {
		this.highPusher = highPusher;
	}

	public GetCnvdMsgAction getCnvdMsger() {
		return cnvdMsger;
	}

	public void setCnvdMsger(GetCnvdMsgAction cnvdMsger) {
		this.cnvdMsger = cnvdMsger;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			System.out.println("===========" + new Date()
					+ ":sendCnvdMsg start ==============");

			highPusher.saveHighPush();
			cnvdMsger.sendMsgToWechat();
			//actionLoger.logForTask("定时任务", "SendMsgTask", "CNVD每小时采集一次！");
			System.out.println("===========" + new Date()
					+ ":sendCnvdMsg end ==============");
		} catch (Exception e) {
			e.printStackTrace();
			throw new JobExecutionException();
		}
	}
}
