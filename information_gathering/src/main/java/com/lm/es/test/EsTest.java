package com.lm.es.test;


import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.utils.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

import com.lm.es.init.TransClient;

public class EsTest {
    private final static Logger log = LogManager.getLogger(EsTest.class);

	@Test
	public void t(){
		try {
			XContentBuilder xContentBuilder = jsonBuilder().startObject()
							.field("usr", "liming")
							.field("age", "24")
							.field("gender", "m")
						.endObject();
		
			IndexRequestBuilder builder = TransClient.getClient().prepareIndex("spring", "test", DateUtils.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss"));
			IndexResponse res = builder.setSource(xContentBuilder).get();

			XContentBuilder xContentBuilder1 = jsonBuilder().startObject()
					.field("usr", "liming1")
					.field("age", "25")
					.field("gender", "m")
					.endObject();
			IndexRequestBuilder builder1 = TransClient.getClient().prepareIndex("spring", "test", "2");
			IndexResponse res1 = builder1.setSource(xContentBuilder1).get();
			log.info("获取es返回的数据" + res1.toString());
			
			GetResponse getResponse = TransClient.getClient().prepareGet("spring", "test", "1").get();
			log.info("getRequest返回数据:" + getResponse);
			
			UpdateResponse updateResponse = TransClient.getClient().prepareUpdate("spring", "test", "1")
									.setDoc(jsonBuilder()
												.startObject()
													.field("age","25")
												.endObject()).get();
			log.info("updateResponse返回数据:" + updateResponse);
			
			DeleteResponse deleteResponse = TransClient.getClient().prepareDelete("spring", "test", "2").get();
			log.info("deleteResponse返回数据:" + deleteResponse);
			
			GetResponse res11 = TransClient.getClient().prepareGet("spring", "test", "1").get();
			log.info("res11返回数据:" + res11);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void delete() throws InterruptedException, ExecutionException{
		DeleteIndexResponse	deleteResponse = TransClient.getClient().admin().indices().prepareDelete("jquery").execute().get();
		log.info("respose:" + deleteResponse.toString());
	}
	
	@Test
	public void search(){
		SearchResponse response = TransClient.getClient().prepareSearch("tuicool")
									        .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
									       // .setPostFilter(QueryBuilders.boolQuery().must(new QueryStringQueryBuilder("MySQL").field("body")))
									      .setPostFilter(QueryBuilders.matchQuery("body", "MySQL"))
									        //.setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(28))     // Filter
									        .setFrom(0).setSize(60).setExplain(true)
									        .execute()
									        .actionGet();
		log.info("SearchResponse返回数据:" + response.getHits().getTotalHits());
	}
}
