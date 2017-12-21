package com.cernet.task;
import java.util.Date;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.cernet.web.action.SendLeakMsgAction;
/**
 * 对漏洞等级为10的漏洞的推送6个月以内未修复的漏洞信息1天推送1次 设定是：每天上午九点
 * @author lizk
 *
 */
public class SendScoreMsgTask extends QuartzJobBean{
    private SendLeakMsgAction sendLeakMsger;	
	public SendLeakMsgAction getSendLeakMsger() {
		return sendLeakMsger;
	}

	public void setSendLeakMsger(SendLeakMsgAction sendLeakMsger) {
		this.sendLeakMsger = sendLeakMsger;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		 try {
			    System.out.println("===========" + new Date()+ ":sendScoreMsg start ==============");
			    sendLeakMsger.sendLeakMsgOnce();
				System.out.println("===========" + new Date()+ ":sendScoreMsg end ==============");
		} catch (Exception e) {
			e.printStackTrace();
			throw new JobExecutionException();
		}   
	}
}
