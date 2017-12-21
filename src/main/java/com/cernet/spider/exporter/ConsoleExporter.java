/**
 * 
 */
package com.cernet.spider.exporter;

import com.cernet.spider.Article;

/**
 * Created by Eclipse.
 *
 * @author Gump
 * @date   2013-11-1
 */
public class ConsoleExporter implements Exporter {
  public void export(Article article){
		System.out.println("URL: " + article.getSrcURL());
		System.out.println("ColId: " + article.getColId());
		System.out.println("Title: " + article.getTitle());
		System.out.println("Content: " + article.getContent());
		System.out.println("Pubtime: " + article.getPubTime());
		System.out.println("From： " + article.getFrom());
		System.out.println("PickTime: " + article.getPickTime());
		System.out.println();
  }
}
