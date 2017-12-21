package com.cernet.task;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.cernet.spider.Spider;

public class GrapTask extends QuartzJobBean {
	private Spider spider;

	public void setSpider(Spider spider) {
		this.spider = spider;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {

		try {
			System.out.println("===========" + new Date()
					+ ":spider start ==============");
			spider.run();
			System.out.println("===========" + new Date()
					+ ":spider end ==============");
		} catch (Exception e) {
			e.printStackTrace();
			throw new JobExecutionException();
		}
	}
}
