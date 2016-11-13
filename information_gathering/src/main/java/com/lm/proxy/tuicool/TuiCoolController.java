package com.lm.proxy.tuicool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lm.commons.IfgConstants;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

@Service("tuiCoolController")
public class TuiCoolController {
    private final static Logger log = LogManager.getLogger(TuiCoolController.class);

    @Value("${tuicool.pages}")
    private String pages;
    
    public void getTuiCoolTechArticle(){
    	
        String crawlStorageFolder = IfgConstants.CRAWL_STORAGE_FOLDER;
        int numberOfCrawlers = 5;

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(crawlStorageFolder);
        config.setMaxDepthOfCrawling(1);
        config.setPolitenessDelay(1000);
        
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        robotstxtConfig.setEnabled(false); //禁用爬虫的检测网站是否允许爬的功能
        CrawlController controller;
		try {
			controller = new CrawlController(config, pageFetcher, robotstxtServer);
			for(int i=0; i<Integer.parseInt(pages); i++){
				controller.addSeed("http://www.tuicool.com/ah/20/" + i + "?lang=1");
			}
			
			controller.start(TuiCoolCrawler.class, numberOfCrawlers);
		} catch (Exception e) {
			log.error("爬取推酷技术文章出错", e);
		}
    }
    
}