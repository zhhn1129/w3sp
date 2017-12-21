/**
 * 
 */
package com.cernet.spider;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;



import com.cernet.spider.exporter.ConsoleExporter;
import com.cernet.spider.exporter.DataBaseExporter;
import com.cernet.spider.exporter.Exporter;
import com.cernet.spider.rule.ArticleListRule;
import com.cernet.spider.rule.ArticleViewRule;
import com.cernet.spider.rule.ContentRule;
import com.cernet.spider.rule.FromRule;
import com.cernet.spider.rule.PageRule;
import com.cernet.spider.rule.PubTimeRule;
import com.cernet.spider.rule.Rule;
import com.cernet.spider.rule.TitleRule;
import com.cernet.util.JoinUrl;

/**
 * Created by Eclipse.
 * 
 * @author Gump
 * @date 2013-10-31
 */
public class Spider {
	private final static Log log = LogFactory.getLog(Spider.class);
	private File taskFiles;
	private Exporter exporter;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Spider spider = new Spider();

		spider.setExporter(new ConsoleExporter());	//输出到控制台
		//spider.setExporter(new DataBaseExporter());	//保存到后台数据表里
		File files = new File("E:\\IPOP\\w3sp-20170608\\src\\main\\resources\\taskFilesNew");
		File[] tempList = files.listFiles();
		System.out.println("该目录下对象个数："+tempList.length);
		spider.setTaskFiles(files);
		


		List<Task> tasks = spider.loadTasks();
		for (Task task : tasks) {
			try {
				spider.runTask(task);
			} catch (Exception e) {
				log.error("fail to run the task. task's url: "
						+ task.getColumnURL());
				e.printStackTrace();
			}

		}
	}

	// 用于采集器调用的方法
	public void run() {

		List<Task> tasks = loadTasks();
		for (Task task : tasks) {
			try {
				runTask(task);
			} catch (Exception e) {
				log.error("fail to run the task. task's url: "
						+ task.getColumnURL());
				e.printStackTrace();
			}

		}
	}

	public List<Task> loadTasks() {
		List<Task> tasks = new ArrayList<Task>();
		File[] files = taskFiles.listFiles();

		for (int i = 0; i < files.length; i++) {
			tasks.add(parseTask(files[i]));
		}
		return tasks;
	}

	public String getAttrVal(Document document, String attrXpath) {
		String attrVal = "";
		List list = document.selectNodes(attrXpath);
		Iterator iter = list.iterator();
		if (iter.hasNext()) {
			Attribute attribute = (Attribute) iter.next();
			attrVal = attribute.getValue();
		}
		return attrVal;
	}

	public String getText(Document document, String attrXpath) {
		String text = "";
		List list = document.selectNodes(attrXpath);
		Iterator iter = list.iterator();
		if (iter.hasNext()) {
			Element element = (Element) iter.next();
			text = element.getText();
		}
		return text;
	}

	public List<String> getFiltedStrs(Document document, String attrXpath) {
		List<String> filtedStrs = new ArrayList<String>();

		List list = document.selectNodes(attrXpath);
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			Element element = (Element) iter.next();
			filtedStrs.add(element.getText());
		}
		return filtedStrs;
	}

	public Task parseTask(File file) {
		log.info("Starting to parse taskfile, file: " + file.getAbsolutePath());
		Task task = new Task();

		SAXReader saxReader = new SAXReader();
		Document document = null;
		try {
			document = saxReader.read(file);

			// load the attributes of task
			task.setColumnId(Integer.valueOf(getAttrVal(document,
					"/task/@columnId")));
			task.setColumnURL(getAttrVal(document, "/task/@columnURL"));
			task.setDesc(getAttrVal(document, "/task/@desc"));
			task.setEncoding(getAttrVal(document, "/task/@encoding"));
			task.setDelayTime(Integer.valueOf(getAttrVal(document,
					"/task/@delayTime")));
			task.setPageModel(getAttrVal(document, "/task/@pageModel"));

			String startPageIndexStr = getAttrVal(document,
					"/task/@startPageIndex");

			if (!isEmptyStr(startPageIndexStr))
				task.setStartPageIndex(Integer.valueOf(startPageIndexStr));

			String endPageIndexStr = getAttrVal(document, "/task/@endPageIndex");
			if (!isEmptyStr(endPageIndexStr))
				task.setEndPageIndex(Integer.valueOf(endPageIndexStr));

			// load the rule of article list
			ArticleListRule alr = new ArticleListRule();
			alr.setStartStr(getText(document, "/task/articleList/startStr"));
			alr.setEndStr(getText(document, "/task/articleList/endStr"));
			alr.setIncludedStrOfURL(getText(document,
					"/task/articleList/includedStrOfURL"));
			alr.setExcludedStrOfURL(getText(document,
					"/task/articleList/excludedStrOfURL"));
			task.setArticleListRule(alr);

			// load the rule of article view
			ArticleViewRule avr = new ArticleViewRule();
			avr.setStartStr(getText(document, "/task/articleView/startStr"));
			avr.setEndStr(getText(document, "/task/articleView/endStr"));

			// load the rule of article title
			TitleRule tr = new TitleRule();
			tr.setStartStr(getText(document, "/task/articleView/title/startStr"));
			tr.setEndStr(getText(document, "/task/articleView/title/endStr"));
			tr.setPrefix(getText(document, "/task/articleView/title/prefix"));
			tr.setSuffix(getText(document, "/task/articleView/title/suffix"));
			tr.setFiltedStrs(getFiltedStrs(document,
					"/task/articleView/title/filtedStrs/filtedStr"));
			avr.setTitleRule(tr);

			// load the rule of article content
			ContentRule cr = new ContentRule();
			cr.setStartStr(getText(document,
					"/task/articleView/content/startStr"));
			cr.setEndStr(getText(document, "/task/articleView/content/endStr"));
			cr.setFiltedStrs(getFiltedStrs(document,
					"/task/articleView/content/filtedStrs/filtedStr"));
			PageRule pr = new PageRule();
			pr.setSignStr(getText(document,
					"/task/articleView/content/page/signStr"));
			pr.setStartStr(getText(document,
					"/task/articleView/content/page/startStr"));
			pr.setEndStr(getText(document,
					"/task/articleView/content/page/endStr"));
			pr.setIncludedStrOfURL(getText(document,
					"/task/articleView/content/page/includedStrOfURL"));
			pr.setExcludedStrOfURL(getText(document,
					"/task/articleView/content/page/excludedStrOfURL"));
			cr.setPageRule(pr);
			avr.setContentRule(cr);

			// load the rule of article public time
			PubTimeRule ptr = new PubTimeRule();
			ptr.setStartStr(getText(document,
					"/task/articleView/pubTime/startStr"));
			ptr.setEndStr(getText(document, "/task/articleView/pubTime/endStr"));
			ptr.setDateFormat(getText(document,
					"/task/articleView/pubTime/dateFormat"));
			avr.setPubTimeRule(ptr);

			// load the rule of article source
			FromRule fr = new FromRule();
			fr.setStartStr(getText(document, "/task/articleView/from/startStr"));
			fr.setEndStr(getText(document, "/task/articleView/from/endStr"));
			fr.setPrefix(getText(document, "/task/articleView/from/prefix"));
			fr.setSuffix(getText(document, "/task/articleView/from/suffix"));
			fr.setFiltedStrs(getFiltedStrs(document,
					"/task/articleView/from/filtedStrs/filtedStr"));
			avr.setFromRule(fr);
			task.setArticleViewRule(avr);
			log.info("Finished parsing taskfile, file: "
					+ file.getAbsolutePath());

		} catch (DocumentException e) {
			log.error("The file can not be read. file:"
					+ file.getAbsolutePath());
			e.printStackTrace();
		} catch (Exception e) {
			log.error("fail to load the task, file: " + file.getAbsolutePath());
			e.printStackTrace();
		}

		return task;
	}

	public void runTask(Task task) {
		try {

			int startPageIndex = task.getStartPageIndex();
			int endPageIndex = task.getEndPageIndex();
			String pageModel = task.getPageModel();
			// handle multi-page article list
			if (!isEmptyStr(pageModel) && startPageIndex > 1
					&& endPageIndex > 1 && endPageIndex >= startPageIndex) {
				for (int i = endPageIndex; i >= startPageIndex; i--) {
					String currPageURL = task.getColumnURL()
							+ pageModel.replace("(*)", String.valueOf(i - 1));
 					task.setCurrPageURL(currPageURL);
					runTask0(task);
				}
			}

			task.setCurrPageURL(task.getColumnURL());
			runTask0(task);

		} catch (Exception e) {
			log.error("fail to run the task. task's url: "
					+ task.getColumnURL());
			e.printStackTrace();
		}

	}

	public void runTask0(Task task) {
		try {
			setupUrlList(task); // get all article URL need to collect.

			List<String[]> articleUrls = task.getArticleUrls();

			for (int i = articleUrls.size() - 1; i >= 0; i--) {

				Article article = extractArticle(articleUrls.get(i)[0], task);
				if (article != null && exporter != null) {
					exporter.export(article);
				}
			}
		} catch (Exception e) {
			log.error("fail to run the task. task's url: "
					+ task.getColumnURL());
			e.printStackTrace();
		}

	}

	public Article extractArticle(String url, Task task) {
		try {
			// to pick up the next article after delayTime.
			if (task.getDelayTime() > 0)
				try {
					Thread.sleep(1000 * task.getDelayTime());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			Article article = pickArticle(task, url);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			article.setPickTime(sdf.format(new Date()));
			article.setColId(task.getColumnId());
			article.setSrcURL(url);
			return article;
		} catch (Exception e) {
			log.error("fail to extract the article, url: " + url);
			e.printStackTrace();
			return null;
		}
	}

	public void printArticle(Article article) {
		System.out.println("URL: " + article.getSrcURL());
		System.out.println("ColId: " + article.getColId());
		System.out.println("Title: " + article.getTitle());
		System.out.println("Content: " + article.getContent());
		System.out.println("Pubtime: " + article.getPubTime());
		System.out.println("From： " + article.getFrom());
		System.out.println("PickTime: " + article.getPickTime());
		System.out.println();
	}

	public Article pickArticle(Task task, String url) {
		String htmlStr = Crawl.getHtml(url, task.getArticleViewRule()
				.getStartStr(), task.getArticleViewRule().getEndStr(), task
				.getEncoding());
		Article article = new Article();
		article.setTitle(pickTitle(task.getArticleViewRule().getTitleRule(),
				htmlStr));
		article.setContent(pickContentsWithPages(task, url, htmlStr));
		article.setPubTime(pickPubTime(task.getArticleViewRule()
				.getPubTimeRule(), htmlStr));
		article.setFrom(pickFrom(task.getArticleViewRule().getFromRule(),
				htmlStr));
		return article;
	}

	public String pickTitle(TitleRule tr, String htmlStr) {
		String title = "";
		title = pickSegment(tr, htmlStr);
		// maybe do something

		return title;
	}

	public String pickContentsWithPages(Task task, String url, String htmlStr) {
		String content = "";
		ContentRule cr = task.getArticleViewRule().getContentRule();
		content = pickContent(cr, htmlStr);

		PageRule pr = cr.getPageRule();

		// handle pages
		if (!isEmptyStr(pr.getSignStr())) {

			String pageHtml = Crawl.getHtml(url, pr.getStartStr(),
					pr.getEndStr(), task.getEncoding());
			List<String[]> pageUrls = new ArrayList<String[]>();
			
			//文章分页采集规则发生变化，需要特殊处理，手动拼接pageUrls
			String page = pageHtml.split("\"")[1];
			if("var _PAGE_COUNT=".equals(pr.getSignStr())&&!"1".equals(page)){
				//分页表示为它的并且截取数字不等于1的即为特殊分页规则
				int pages = Integer.parseInt(page);
				pageUrls.add(new String[]{url});
				for (int i = 1; i < pages; i++) {
					pageUrls.add(new String[]{url.split(".shtml")[0]+"_"+i+".shtml"});
				}
				
			}else{
				pageUrls = Crawl.getUrls(url,pageHtml,task.getEncoding());
			}

			
			List<String> newPageUrls = new ArrayList<String>();

			// merge the multiple pages
			if (pageUrls != null) {
				// remove the html code about the page
				String startStr = pr.getStartStr().replaceAll("\r|\n", "");
				if (content.indexOf(startStr) > 0)
					content = content.substring(0, content.indexOf(startStr));

				for (String[] strs : pageUrls) {
					String aUrl = strs[0];
					if (!isEmptyStr(pr.getIncludedStrOfURL())
							&& !aUrl.contains(pr.getIncludedStrOfURL()))
						continue;
					if (!isEmptyStr(pr.getExcludedStrOfURL())
							&& aUrl.contains(pr.getExcludedStrOfURL()))
						continue;
					newPageUrls.add(aUrl);
					log.debug("a url of paged article: " + aUrl);
				}

				// append the content
				for (String aPageUrl : newPageUrls) {
					String aHtmlStr = Crawl.getHtml(aPageUrl, task
							.getArticleViewRule().getStartStr(), task
							.getArticleViewRule().getEndStr(), task
							.getEncoding());
					String aPageContent = pickContent(cr, aHtmlStr);

					// remove the html code about the page
					if (aPageContent.indexOf(startStr) > 0)
						aPageContent = aPageContent.substring(0,
								aPageContent.indexOf(startStr));

					content += aPageContent;
				}

			}
		}
		//正文中链接通过正则匹配出来并替换成完整路径
		JoinUrl joinUrl = new JoinUrl();
		Pattern p = Pattern.compile("(href|src)\\s*=\\s*['\"]([^'\"]+)['\"]\\s*");
		Matcher m = p.matcher(content);
		while (m.find()) {
			content = content.replaceAll(m.group(),m.group().replaceAll(m.group(2), joinUrl.joinUrl(url,m.group(2))));	
		}
		return content;

	}

	public String pickContent(ContentRule cr, String htmlStr) {
		String content = "";
		content = pickSegment(cr, htmlStr);
		return content;

	}

	public String pickPubTime(PubTimeRule pr, String htmlStr) {
		String segment = "";
		String expStr = "";
		segment = pickSegment(pr, htmlStr);
		if (!isEmptyStr(pr.getDateFormat())) {
			expStr = pr.getDateFormat().toLowerCase().replace("yyyy", "\\d{4}")
					.replace("mm", "\\d{2}").replace("dd", "\\d{2}")
					.replace("hh", "\\d{2}").replace("ss", "\\d{2}");
			Pattern p = Pattern.compile(expStr);
			Matcher m = p.matcher(segment);
			if (m.find()) {
				segment = m.group();
			}
		}
		return segment;
	}

	public String pickFrom(FromRule fr, String htmlStr) {
		return pickSegment(fr, htmlStr);
	}

	public boolean isEmptyStr(String str) {
		return str == null || str.trim().isEmpty();
	}

	public String pickSegment(Rule r, String htmlStr) {
		String segment = "";
		String startStr = r.getStartStr().replaceAll("\r|\n|\t", "");
		String endStr = r.getEndStr().replaceAll("\r|\n|\t", "");
		htmlStr = htmlStr.replaceAll("\r|\n|\t", "");

		Pattern p = Pattern.compile(startStr + "(.+)" + endStr);
		Matcher m = p.matcher(htmlStr);
		if (m.find()) {
			segment = m.group(1);
		}
		if (!isEmptyStr(r.getPrefix())) {
			// segment=segment.substring(r.getPrefix().length());
			segment = segment.replaceAll("^" + r.getPrefix(), "");

		}

		if (!isEmptyStr(r.getSuffix())) {
			// segment=segment.substring(0,segment.lastIndexOf(r.getSuffix()));
			segment = segment.replaceAll(r.getSuffix() + "$", "");
		}
		// TODO:
		// handle the filtedStr

		return segment;
	}

	public void pushDB(Article article) {

	}

	/**
	 * get all urls of article to pick up
	 * 
	 * @param task
	 */
	public void setupUrlList(Task task) {
		
		System.out.println(task.getCurrPageURL());
		
		
		String html = Crawl.getHtml(task.getCurrPageURL(), task
				.getArticleListRule().getStartStr(), task.getArticleListRule()
				.getEndStr(), task.getEncoding());
		List<String[]> urls = Crawl.getUrls(task.getCurrPageURL(), html,
				task.getEncoding());
		List<String[]> newUrls = new ArrayList<String[]>();
		ArticleListRule alr = task.getArticleListRule();
		for (String[] strs : urls) {
			String aUrl = strs[0];

			if (!isEmptyStr(alr.getIncludedStrOfURL())
					&& !aUrl.contains(alr.getIncludedStrOfURL()))
				continue;
			if (!isEmptyStr(alr.getExcludedStrOfURL())
					&& aUrl.contains(alr.getExcludedStrOfURL()))
				continue;
			newUrls.add(strs);
		}

		task.setArticleUrls(newUrls);
	}

	public void setTaskFiles(File taskFiles) {
		this.taskFiles = taskFiles;
	}

	public Exporter getExporter() {
		return exporter;
	}

	public void setExporter(Exporter exporter) {
		this.exporter = exporter;
	}
}
