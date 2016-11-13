package com.lm.es.util;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.lm.commons.IfgConstants;
import com.lm.enums.IndexAndType;
import com.lm.es.init.TransClient;

public class EsClientUtils extends TransClient {
	private final static Logger log = LogManager.getLogger(EsClientUtils.class);

	/**
	 * 将爬到的数据保存到es
	 * 
	 * @Title: saveTuiColl
	 * @Description: TODO
	 */
	public static void saveTuiColl(String title, String body, String url,
			IndexAndType indexAndType, String index) {
		try {
			XContentBuilder xContentBuilder = jsonBuilder().startObject()
					.field(IfgConstants.TITLE, title).field(IfgConstants.BODY, body)
					.field(IfgConstants.URL, url).endObject();
			IndexRequestBuilder builder = TransClient.getClient().prepareIndex(index,
					indexAndType.getType(),
					DateFormatUtils.format(new Date(), IfgConstants.FORMAT_DATETIME_WITH_SPLIT));
			IndexResponse res = builder.setSource(xContentBuilder).get();

			log.info(MessageFormat.format("保存数据到es结果:{0}, title:{1}", res.getResult(), title));
		} catch (IOException e) {
			log.error("保存数据到es异常，异常原因:", e);
		}

	}

	/**
	 * 检查指定字段的文章是否存在
	 * @param index 
	 */
	public static long checkIsExists(IndexAndType indexAndType, String filed, String value,
			String index) {
		try {
			SearchResponse response = TransClient.getClient().prepareSearch(index)
					.setTypes(indexAndType.getType())
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
					.setPostFilter(QueryBuilders.matchQuery(filed, value)).setExplain(true)
					.execute().actionGet();
			return response.getHits().getTotalHits();
		} catch (Exception e) {
			log.info("根据index和type去es查询数据出错，直接返回未找到已匹配的数据。 错误原因" + e.getMessage());
			return 0;
		}
	}
}
