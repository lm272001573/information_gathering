package com.lm.proxy.tuicool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.lm.base.BaseTest;
import com.lm.commons.IfgConstants;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class TuiCoolTest extends BaseTest {
	private final static Logger log = LogManager.getLogger(TuiCoolTest.class);

	@Test
	public void getTuiCool(){
        String crawlStorageFolder = IfgConstants.CRAWL_STORAGE_FOLDER;
        int numberOfCrawlers = 5;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(1);
        config.setPolitenessDelay(1000);
        
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        robotstxtConfig.setEnabled(true); //禁用爬虫的检测网站是否允许爬的功能
        CrawlController controller;
		try {
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
			//推酷技术文章
			for(int i=0; i< 1; i++){
				controller.addSeed("http://www.tuicool.com/ah/20/" + i + "?lang=1");
			}
			
			//推酷热门文章
			for(int i=0; i< 10; i++){
				controller.addSeed("http://www.tuicool.com/ah/0/" + i + "?lang=1");
			}
			controller.start(TuiCoolCrawler.class, numberOfCrawlers);
		} catch (Exception e) {
			log.error("爬取推酷技术文章出错", e);
		}
    
	}
}
