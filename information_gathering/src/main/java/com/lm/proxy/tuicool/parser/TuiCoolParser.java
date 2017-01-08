package com.lm.proxy.tuicool.parser;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.lm.commons.IfgConstants;
import com.lm.enums.IndexAndType;
import com.lm.es.util.EsClientUtils;

/**
 * 解析推酷的数据
 * <p>
 * 
 * @version 1.0.0,2016年11月13日
 * @author liming
 */
public class TuiCoolParser {
	private final static Logger log = LogManager.getLogger(TuiCoolParser.class);

	public static void parseHtml(String html, String url) {
		try {

			Document doc = Jsoup.parse(html);
			Elements element = doc.select("h1");
			if (element.size() > 0) {
				String title = element.get(0).text();

				if (StringUtils.isNotEmpty(title)) {

					Elements articleBody = doc.select(".article_body");
					String body = null;
					if (null != articleBody && null != articleBody.get(0)) {
						body = articleBody.get(0).text();
					} else {
						return;
					}

					log.info(MessageFormat.format("get tuicool title{0}, url:{1}", title, url));
					for (IndexAndType indexAndType : IndexAndType.values()) {
						if (title.toLowerCase().contains(indexAndType.getType().toLowerCase())) {
							log.info("already match title:" + title);
							
							long countExists = 1;
							// 存入es前检查title是否存在
							countExists = EsClientUtils.checkIsExists(indexAndType,
									IfgConstants.TITLE, title, IfgConstants.ARTICLE_SOURCE_TUICOOL_TECHNICAL);
							if (countExists == 0) {
								log.info("将title存如elasticSearch, title:" + title);
									EsClientUtils.saveTuiColl(title, body, url, indexAndType, IfgConstants.ARTICLE_SOURCE_TUICOOL_TECHNICAL);
							}else{
								log.info("title在es中已存在:" + title);
							}
						}
					}
				} else {
					log.info("未找到title");
				}
			}else{
				log.info("解析推酷返回页面失败，html:" + html);
			}
		} catch (Exception e) {
			log.error("接触推酷文章出错，错误原因:", e);
		}
	}
	
}
