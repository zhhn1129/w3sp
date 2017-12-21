package com.cernet.task;
import java.util.Date;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import com.cernet.web.action.SendLeakMsgAction;
/**
 * 
 * @author lizk
 * 对漏洞等级为10的漏洞的推送6个月以上未修复的漏洞信息1天推送2次 设定是：每天上午九点 和下午五点
 */
public class SendScoMsgTask extends QuartzJobBean{
 private SendLeakMsgAction sendLeakMsgerTwice;
	public SendLeakMsgAction getSendLeakMsgerTwice() {
	return sendLeakMsgerTwice;
}
public void setSendLeakMsgerTwice(SendLeakMsgAction sendLeakMsgerTwice) {
	this.sendLeakMsgerTwice = sendLeakMsgerTwice;
}
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		try {
			System.out.println("===========" + new Date()+ ":sendScoMsg start ==============");
		    sendLeakMsgerTwice.sendLeakMsgTwice();
			System.out.println("===========" + new Date()+ ":sendScoMsg end ==============");
		} catch (Exception e) {
			e.printStackTrace();
			throw new JobExecutionException();
		}
	}
}
